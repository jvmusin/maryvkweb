package my.maryvk.maryvkweb.web

data class SeekerStatus(val userId: Int, val isRunning: Boolean) {
    val status: Status

    init {
        this.status = if (isRunning) Status.RUNNING else Status.STOPPED
    }

    fun changeStateStr(): String {
        return if (status == Status.RUNNING) "stop" else "start"
    }

    enum class Status {
        RUNNING,
        STOPPED
    }
}