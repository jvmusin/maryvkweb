package my.maryvk.maryvkweb.repository

import my.maryvk.maryvkweb.domain.RegisteredSeeker
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface RegisteredSeekerRepository : JpaRepository<RegisteredSeeker, Long> {
    @Transactional
    fun deleteByTargetId(targetId: Int?)
}