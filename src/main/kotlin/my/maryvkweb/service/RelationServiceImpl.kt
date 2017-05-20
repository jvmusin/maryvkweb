package my.maryvkweb.service

import my.maryvkweb.domain.Relation
import my.maryvkweb.domain.RelationChange
import my.maryvkweb.domain.RelationType
import my.maryvkweb.repository.RelationRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service class RelationServiceImpl(
        private val relationRepository: RelationRepository,
        private val relationChangeService: RelationChangeService
) : RelationService {

    override fun findAllFor(userId: Int, relationType: RelationType) =
            relationRepository
                    .findAllByOwnerIdAndRelationTypeOrderByTargetId(userId, relationType)
                    .map { it.targetId!! }

    override fun addRelation(relation: Relation) {
        relationRepository.saveAndFlush(relation)
        relationChangeService.registerChange(relation.createRelationChange(isAppeared = true))
    }

    override fun removeRelation(relation: Relation) {
        relationRepository.deleteByOwnerIdAndTargetIdAndRelationType(relation.ownerId!!, relation.targetId!!, relation.relationType!!)
        relationChangeService.registerChange(relation.createRelationChange(isAppeared = false))
    }

    private fun Relation.createRelationChange(isAppeared: Boolean) = RelationChange(
            time = LocalDateTime.now(),
            ownerId = ownerId,
            targetId = targetId,
            relationType = relationType,
            isAppeared = isAppeared
    )
}