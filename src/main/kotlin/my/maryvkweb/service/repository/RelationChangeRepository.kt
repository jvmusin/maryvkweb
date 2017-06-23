package my.maryvkweb.service.repository

import my.maryvkweb.domain.RelationChange
import org.springframework.data.jpa.repository.JpaRepository

interface RelationChangeRepository : JpaRepository<RelationChange, Long> {
    fun findAllByConnectedIdOrderByIdDesc(connectedId: Int): List<RelationChange>
}