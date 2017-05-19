package my.maryvkweb.service

import my.maryvkweb.domain.Relation
import my.maryvkweb.domain.RelationType
import my.maryvkweb.repository.RelationRepository
import org.springframework.stereotype.Service

@Service class RelationServiceImpl(
        private val relationRepository: RelationRepository,
        private val relationChangeService: RelationChangeService
) : RelationService {

    override fun findAllFor(userId: Int, relationType: RelationType): List<Int> {
        return relationRepository.findAllByOwnerIdAndRelationTypeOrderByTargetId(userId, relationType)
                .map { it.targetId!! }
                .toList()
    }

    override fun addRelation(relation: Relation) {
        relationRepository.saveAndFlush(relation)
        relationChangeService.registerChange(relation.createRelationChange(true))
    }

    override fun removeRelation(relation: Relation) {
        relationRepository.deleteByOwnerIdAndTargetIdAndRelationType(relation.ownerId!!, relation.targetId!!, relation.relationType!!)
        relationChangeService.registerChange(relation.createRelationChange(false))
    }
}