package forge.server.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import forge.deck.Deck;
import forge.game.GameRules;
import forge.game.GameType;
import forge.game.player.RegisteredPlayer;
import forge.server.core.SimulationsQueue;
import forge.server.model.SimulationStartRequest;
import forge.server.model.SimulationStartResponse;
import forge.server.model.SimulationStatusResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimulatorController {

    @PostMapping("/simulation")
    public SimulationStartResponse startSimulation(
            @RequestBody SimulationStartRequest request
            ) {
        long simId = SimulationsQueue.getInstance().start(request);
        if(simId > 0) {
            return new SimulationStartResponse(simId, "Simulation started", true);
        }

        return new SimulationStartResponse(simId, "Server busy", false);
    }

    @GetMapping("/simulation/{id}")
    public SimulationStatusResponse getSimulationStatus(@PathVariable Long id) {
        return null;
    }
}
