package my.maryvk.maryvkweb.repository

import my.maryvk.maryvkweb.domain.RelationChange
import org.springframework.data.jpa.repository.JpaRepository

interface RelationChangeRepository : JpaRepository<RelationChange, Long> {
    fun findAllByOwnerIdOrderByTimeDesc(ownerId: Int?): List<RelationChange>
    fun findAllByOrderByTimeDesc(): List<RelationChange>
}