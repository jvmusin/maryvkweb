package my.maryvk.maryvkweb.repository

import my.maryvk.maryvkweb.domain.Relation
import my.maryvk.maryvkweb.domain.RelationType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface RelationRepository : JpaRepository<Relation, Long> {
    fun findAllByOwnerIdAndRelationTypeOrderByTargetId(ownerId: Int?, relationType: RelationType): List<Relation>
    @Transactional
    fun deleteByOwnerIdAndTargetIdAndRelationType(ownerId: Int?, targetId: Int?, relationType: RelationType)
}