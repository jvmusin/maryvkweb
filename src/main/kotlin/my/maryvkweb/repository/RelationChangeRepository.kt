package my.maryvkweb.repository

import my.maryvkweb.domain.RelationChange
import org.springframework.data.jpa.repository.JpaRepository

interface RelationChangeRepository : JpaRepository<RelationChange, Long> {
    fun findAllByConnectedIdOrderByTimeDesc(connectedId: Int): List<RelationChange>
    fun findAllByOrderByTimeDesc(): List<RelationChange>
}