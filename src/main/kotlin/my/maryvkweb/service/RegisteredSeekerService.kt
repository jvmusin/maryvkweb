package my.maryvkweb.service

import my.maryvkweb.domain.RegisteredSeeker

interface RegisteredSeekerService {
    fun register(connectedId: Int)
    fun unregister(connectedId: Int)
    fun findAll(): List<RegisteredSeeker>
}