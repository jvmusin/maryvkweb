package my.maryvk.maryvkweb.seeker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class MarySeekerSchedulerImpl implements MarySeekerScheduler {

    @Value("${vk.default-period-to-seek}")
    private int periodToSeek;

    private final MarySeekerFactory marySeekerFactory;

    private final ThreadPoolTaskScheduler taskScheduler;
    private final Map<Integer, ScheduledFuture<?>> scheduledSeekers;

    @Autowired
    public MarySeekerSchedulerImpl(MarySeekerFactory marySeekerFactory) {
        this.marySeekerFactory = marySeekerFactory;

        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.initialize();
        scheduledSeekers = new HashMap<>();
    }

    @Override
    public void schedule(int targetId) {
        if (isRunning(targetId))
            return;
        MarySeeker seeker = marySeekerFactory.create(targetId);
        ScheduledFuture<?> task = taskScheduler.scheduleAtFixedRate(seeker::seek, periodToSeek);
        scheduledSeekers.put(targetId, task);
    }

    @Override
    public void unschedule(int targetId) {
        if (!isRunning(targetId))
            return;
        scheduledSeekers.remove(targetId).cancel(true);
    }

    @Override
    public boolean isRunning(int targetId) {
        return scheduledSeekers.containsKey(targetId);
    }
}
