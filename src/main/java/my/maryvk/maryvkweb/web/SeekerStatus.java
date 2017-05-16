package my.maryvk.maryvkweb.web;

import lombok.Data;

@Data
public class SeekerStatus {
    private final int userId;
    private final Status status;

    public SeekerStatus(int userId, boolean isRunning) {
        this.userId = userId;
        this.status = isRunning ? Status.RUNNING : Status.STOPPED;
    }

    public String changeStateStr() {
        return status.equals(Status.RUNNING) ? "stop" : "start";
    }

    public enum Status {
        RUNNING,
        STOPPED
    }
}