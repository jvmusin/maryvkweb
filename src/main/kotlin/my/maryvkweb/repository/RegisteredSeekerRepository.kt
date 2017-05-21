package my.maryvkweb.repository

import my.maryvkweb.domain.RegisteredSeeker
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface RegisteredSeekerRepository : JpaRepository<RegisteredSeeker, Long> {
    @Transactional
    fun deleteByConnectedId(connectedId: Int)
}