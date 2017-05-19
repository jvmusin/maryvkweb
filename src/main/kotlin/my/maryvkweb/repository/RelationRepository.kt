package my.maryvkweb.repository

import my.maryvkweb.domain.Relation
import my.maryvkweb.domain.RelationType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface RelationRepository : JpaRepository<Relation, Long> {
    fun findAllByOwnerIdAndRelationTypeOrderByTargetId(ownerId: Int, relationType: RelationType): List<Relation>
    @Transactional
    fun deleteByOwnerIdAndTargetIdAndRelationType(ownerId: Int, targetId: Int, relationType: RelationType)
}