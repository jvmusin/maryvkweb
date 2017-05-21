package my.maryvkweb.web

data class SeekerStatus(
        val targetId: Int,
        val status: Status
) {

    constructor(connectedId: Int, isRunning: Boolean):
            this(connectedId, status = if (isRunning) Status.RUNNING else Status.STOPPED)

    fun changeStateStr() = if (status == Status.RUNNING) "stop" else "start"

    enum class Status {
        RUNNING,
        STOPPED;
    }
}