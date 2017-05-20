package my.maryvkweb.web

data class SeekerStatus(
        val userId: Int,
        val status: Status
) {

    constructor(userId: Int, isRunning: Boolean):
            this(userId, status = if (isRunning) Status.RUNNING else Status.STOPPED)

    fun changeStateStr() = if (status == Status.RUNNING) "stop" else "start"

    enum class Status {
        RUNNING,
        STOPPED;
    }
}