package forge.server.model;

public class SimulationStatusResponse {
    private final long id;
    private final String status;
    private final String[] log;
    private final boolean finished;
    private final boolean success;

    public SimulationStatusResponse(long id, String status, String[] log, boolean finished, boolean success) {
        this.id = id;
        this.status = status;
        this.log = log;
        this.finished = finished;
        this.success = success;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String[] getLog() {
        return log;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isSuccess() {
        return success;
    }
}
