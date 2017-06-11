package my.maryvkweb.service.repository

import my.maryvkweb.domain.Relation
import my.maryvkweb.domain.RelationType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface RelationRepository : JpaRepository<Relation, Long> {
    fun findAllByConnectedIdAndRelationTypeOrderByTargetId(connectedId: Int, relationType: RelationType): List<Relation>
    @Transactional
    fun deleteByConnectedIdAndTargetIdAndRelationType(connectedId: Int, targetId: Int, relationType: RelationType)
}