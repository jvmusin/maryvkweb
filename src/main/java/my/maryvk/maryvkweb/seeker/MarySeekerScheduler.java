package my.maryvk.maryvkweb.seeker;

public interface MarySeekerScheduler {
    void schedule(int targetId);
    void unschedule(int targetId);
    boolean isRunning(int targetId);
}

