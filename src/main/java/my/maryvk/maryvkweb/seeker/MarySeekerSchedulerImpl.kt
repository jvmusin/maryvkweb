package my.maryvk.maryvkweb.seeker

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Service

import java.util.HashMap
import java.util.concurrent.ScheduledFuture

@Service
class MarySeekerSchedulerImpl @Autowired
constructor(private val marySeekerFactory: MarySeekerFactory) : MarySeekerScheduler {

    @Value("\${vk.default-period-to-seek}")
    private val periodToSeek: Int = 0

    private val taskScheduler: ThreadPoolTaskScheduler = ThreadPoolTaskScheduler()
    private val scheduledSeekers: MutableMap<Int, ScheduledFuture<*>>

    init {
        taskScheduler.initialize()
        scheduledSeekers = HashMap<Int, ScheduledFuture<*>>()
    }

    override fun schedule(targetId: Int) {
        if (isRunning(targetId))
            return
        val seeker = marySeekerFactory.create(targetId)
        val task = taskScheduler.scheduleAtFixedRate({ seeker.seek() }, periodToSeek.toLong())
        scheduledSeekers.put(targetId, task)
    }

    override fun unschedule(targetId: Int) {
        if (!isRunning(targetId))
            return
        scheduledSeekers.remove(targetId)?.cancel(true)
    }

    override fun isRunning(targetId: Int): Boolean {
        return scheduledSeekers.containsKey(targetId)
    }
}
