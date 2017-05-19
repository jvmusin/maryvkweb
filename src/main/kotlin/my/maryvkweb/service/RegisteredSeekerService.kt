package my.maryvkweb.service

import my.maryvkweb.domain.RegisteredSeeker

interface RegisteredSeekerService {
    fun register(targetId: Int)
    fun unregister(targetId: Int)
    fun findAll(): List<RegisteredSeeker>
}