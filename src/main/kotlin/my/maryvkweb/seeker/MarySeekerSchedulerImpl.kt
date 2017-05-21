package my.maryvkweb.seeker

import my.maryvkweb.VkProperties
import my.maryvkweb.getLogger
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ScheduledFuture

@Service class MarySeekerSchedulerImpl(
        private val marySeekerFactory: MarySeekerFactory,
        vkProperties: VkProperties
) : MarySeekerScheduler {

    private val log = getLogger<MarySeekerSchedulerImpl>()

    private val periodToSeek = vkProperties.defaultPeriodToSeek

    private val taskScheduler: ThreadPoolTaskScheduler = ThreadPoolTaskScheduler()
    private val scheduledSeekers: MutableMap<Int, ScheduledFuture<*>>

    init {
        taskScheduler.initialize()
        scheduledSeekers = HashMap<Int, ScheduledFuture<*>>()
    }

    override fun schedule(connectedId: Int) {
        if (isRunning(connectedId)) {
            log.info("Failed to schedule for $connectedId: Already running")
            return
        }
        val seeker = marySeekerFactory.create(connectedId)
        val task = taskScheduler.scheduleAtFixedRate(seeker::seek, periodToSeek)
        scheduledSeekers.put(connectedId, task)
        log.info("Scheduled seeker for $connectedId")
    }

    override fun unschedule(connectedId: Int) {
        if (!isRunning(connectedId)) {
            log.info("Failed to unschedule for $connectedId: Not running yet")
            return
        }
        scheduledSeekers.remove(connectedId)?.cancel(true)
        log.info("Unscheduled seeker for $connectedId")
    }

    override fun isRunning(connectedId: Int): Boolean = scheduledSeekers.containsKey(connectedId)
}