package forge.server.model;

public class SimulationStartResponse {
    private final long id;
    private final String status;
    private final boolean success;

    public SimulationStartResponse(long id, String status, boolean success) {
        this.id = id;
        this.status = status;
        this.success = success;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return status;
    }

    public boolean isSuccess() {
        return success;
    }
}
