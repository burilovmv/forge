package forge.server.model;

public class SimulationStartRequest {
    private String type;
    private int games;
    private String[] decks;

    public SimulationStartRequest() {
    }

    public SimulationStartRequest(String type, int games, String[] decks) {
        this.type = type;
        this.games = games;
        this.decks = decks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public String[] getDecks() {
        return decks;
    }

    public void setDecks(String[] decks) {
        this.decks = decks;
    }
}
