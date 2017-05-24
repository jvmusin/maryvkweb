package my.maryvkweb.web

import my.maryvkweb.domain.User

data class SeekerStatus(
        val connected: User,
        val status: Status
) {

    constructor(connected: User, isRunning: Boolean):
            this(connected, status = if (isRunning) Status.RUNNING else Status.STOPPED)

    fun changeStateStr() = if (status == Status.RUNNING) "stop" else "start"

    enum class Status {
        RUNNING,
        STOPPED;
    }
}