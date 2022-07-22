package forge.server.core;

import forge.deck.Deck;
import forge.deck.io.DeckSerializer;
import forge.game.*;
import forge.game.player.RegisteredPlayer;
import forge.model.FModel;
import forge.player.GamePlayerUtil;
import forge.server.model.SimulationStartRequest;
import forge.server.model.SimulationStatusResponse;
import forge.server.util.TimeLimitedCodeBlock;
import forge.util.FileSection;
import forge.util.TextUtil;
import forge.util.WordUtil;
import org.apache.commons.lang3.time.StopWatch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public class SimulationsQueue {
    private static final SimulationsQueue _instance = new SimulationsQueue();

    private static final int MAX_THREADS = 10;

    private final AtomicLong counter = new AtomicLong(1);

    private ConcurrentHashMap <Long, SimulationThread> threads = new ConcurrentHashMap<Long, SimulationThread>();
    private ConcurrentHashMap <Long, SimulationStatusResponse> results = new ConcurrentHashMap <Long, SimulationStatusResponse>();

    public static SimulationsQueue getInstance() {
        return _instance;
    }

    public SimulationStatusResponse getStatus(long id) {
        SimulationThread t = threads.get(id);
        if(t != null) {
            return t.getStatus();
        }

        return results.get(id);
    }

    public long start(SimulationStartRequest params) {
        if(threads.size() >= MAX_THREADS) {
            return -1;
        }

        long id = counter.incrementAndGet();
        SimulationThread t = new SimulationThread(id, params);
        threads.put(id, t);
        t.start();

        return id;
    }

    private class SimulationThread extends Thread {
        private final SimulationStartRequest params;
        private final SimulationStatusResponse status;

        public long getId() {
            return getStatus().getId();
        }

        public SimulationStartRequest getParams() {
            return params;
        }

        public SimulationStatusResponse getStatus() {
            return status;
        }

        public SimulationThread(long id, SimulationStartRequest params) {
            this.params = params;
            status = new SimulationStatusResponse(id, "Starting", new String[]{}, false, false);
        }

        private void simulateSingleMatch(final Match mc, int iGame, List<String> logLines) {
            logLines.add(TextUtil.concatWithSpace("Started game #", String.valueOf(iGame + 1)));
            final StopWatch sw = new StopWatch();
            sw.start();

            final Game g1 = mc.createGame();
            // will run match in the same thread
            try {
                TimeLimitedCodeBlock.runWithTimeout(() -> {
                    mc.startGame(g1);
                    sw.stop();
                }, 120, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                logLines.add("Stopping slow match as draw");
            } catch (Exception | StackOverflowError e) {
                logLines.add(TextUtil.concatNoSpace("Error playing game: ", e.toString()));
            } finally {
                if (sw.isStarted()) {
                    sw.stop();
                }
                if (!g1.isGameOver()) {
                    g1.setGameOver(GameEndReason.Draw);
                }

                List<GameLogEntry> log = g1.getGameLog().getLogEntries(null);
                Collections.reverse(log);

                for (GameLogEntry l : log) {
                    logLines.add(l.toString());
                }

                logLines.add(TextUtil.concatWithSpace("Finished game #", String.valueOf(iGame + 1), " elapsed time: ", sw.formatTime()));
            }
        }

        @Override
        public void run() {
            FModel.initialize(null, null);

            List<RegisteredPlayer> pp = new ArrayList<>();

            GameType type = GameType.Constructed;

            if (params.getType() != null) {
                type = GameType.valueOf(WordUtil.capitalize(params.getType()));
            }

            try {
                for (int i = 0; i < params.getDecks().length; i++) {
                    Deck d = DeckSerializer.fromSections(
                            FileSection.parseSections(
                                    Arrays.asList(params.getDecks()[i].split("\\\\r?\\\\n"))
                            )
                    );

                    if (d == null) {
                        return;
                    }

                    RegisteredPlayer rp;

                    if (type.equals(GameType.Commander)) {
                        rp = RegisteredPlayer.forCommander(d);
                    } else {
                        rp = new RegisteredPlayer(d);
                    }

                    String name = TextUtil.concatNoSpace("Ai(", String.valueOf(i), ")-", d.getName());

                    rp.setPlayer(GamePlayerUtil.createAiPlayer(name, i));
                    pp.add(rp);
                }

                GameRules rules = new GameRules(type);
                rules.setAppliedVariants(EnumSet.of(type));

                Match mc = new Match(rules, pp, "Test");

                List<String> logLines = new ArrayList<String>();

                for (int iGame = 0; iGame < params.getGames(); iGame++) {
                    simulateSingleMatch(mc, iGame, logLines);
                }

                status.setLog(logLines.toArray(new String[] {}));
                status.setSuccess(true);
            } catch (Exception ex) {
                status.setSuccess(false);
                status.setError(ex.toString());
            }

            status.setFinished(true);

            results.put(getId(), status);
            threads.remove(getId());
        }
    }
}
