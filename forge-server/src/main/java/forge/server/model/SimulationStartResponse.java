package forge.server.model;

public class SimulationStartResponse {
    private long id;
    private String status;
    private boolean success;

    public SimulationStartResponse() {
    }

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
