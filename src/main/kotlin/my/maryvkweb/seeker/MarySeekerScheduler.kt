package my.maryvkweb.seeker

interface MarySeekerScheduler {
    fun schedule(connectedId: Int)
    fun unschedule(connectedId: Int)
    fun isRunning(connectedId: Int): Boolean
}