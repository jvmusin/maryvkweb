package my.maryvk.maryvkweb.seeker

interface MarySeekerScheduler {
    fun schedule(targetId: Int)
    fun unschedule(targetId: Int)
    fun isRunning(targetId: Int): Boolean
}