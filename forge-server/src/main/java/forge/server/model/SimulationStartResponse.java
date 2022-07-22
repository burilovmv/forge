package forge.server.model;

public class SimulationStartResponse {
    private final long id;
    private final String content;

    public SimulationStartResponse(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
