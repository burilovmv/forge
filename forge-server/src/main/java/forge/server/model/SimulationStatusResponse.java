package forge.server.model;

public class SimulationStatusResponse {
    private long id;
    private String status;
    private String error;
    private String[] log;
    private boolean finished;
    private boolean success;

    public SimulationStatusResponse() {
    }

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

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String[] getLog() {
        return log;
    }

    public void setLog(String[] log) {
        this.log = log;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
