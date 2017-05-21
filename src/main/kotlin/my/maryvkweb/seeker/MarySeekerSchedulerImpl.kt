package my.maryvkweb.seeker

import my.maryvkweb.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ScheduledFuture

@Service class MarySeekerSchedulerImpl(
        private val marySeekerFactory: MarySeekerFactory
) : MarySeekerScheduler {

    private val log = getLogger<MarySeekerSchedulerImpl>()

    @Value("\${vk.default-period-to-seek}")
    private val periodToSeek: Long = 0

    private val taskScheduler: ThreadPoolTaskScheduler = ThreadPoolTaskScheduler()
    private val scheduledSeekers: MutableMap<Int, ScheduledFuture<*>>

    init {
        taskScheduler.initialize()
        scheduledSeekers = HashMap<Int, ScheduledFuture<*>>()
    }

    override fun schedule(targetId: Int) {
        if (isRunning(targetId)) {
            log.info("Failed to schedule for $targetId: Already running")
            return
        }
        val seeker = marySeekerFactory.create(targetId)
        val task = taskScheduler.scheduleAtFixedRate(seeker::seek, periodToSeek)
        scheduledSeekers.put(targetId, task)
        log.info("Scheduled seeker for $targetId")
    }

    override fun unschedule(targetId: Int) {
        if (!isRunning(targetId)) {
            log.info("Failed to unschedule for $targetId: Not running yet")
            return
        }
        scheduledSeekers.remove(targetId)?.cancel(true)
        log.info("Unscheduled seeker for $targetId")
    }

    override fun isRunning(targetId: Int) = scheduledSeekers.containsKey(targetId)
}