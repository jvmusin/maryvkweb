package my.maryvk.maryvkweb.service

import my.maryvk.maryvkweb.domain.Relation
import my.maryvk.maryvkweb.domain.RelationType
import my.maryvk.maryvkweb.repository.RelationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service class RelationServiceImpl
@Autowired constructor(private val relationRepository: RelationRepository, private val relationChangeService: RelationChangeService) : RelationService {

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