package my.maryvk.maryvkweb.service

import my.maryvk.maryvkweb.domain.RegisteredSeeker

interface RegisteredSeekerService {
    fun register(targetId: Int)
    fun unregister(targetId: Int)
    fun findAll(): List<RegisteredSeeker>
}