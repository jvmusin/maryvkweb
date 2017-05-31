package my.maryvkweb.service.impl

import my.maryvkweb.domain.Relation
import my.maryvkweb.domain.RelationChange
import my.maryvkweb.domain.RelationType
import my.maryvkweb.getCache
import my.maryvkweb.repository.RelationRepository
import my.maryvkweb.service.RelationChangeService
import my.maryvkweb.service.RelationService
import my.maryvkweb.set
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service class RelationServiceImpl(
        private val relationRepository: RelationRepository,
        private val relationChangeService: RelationChangeService,
        cacheManager: CacheManager
) : RelationService {

    private val cache = cacheManager.getCache<RelationServiceImpl>()

    override fun findAllFor(userId: Int, relationType: RelationType): List<Int> {
        return getCachedOrLoad(userId, relationType)
    }

    override fun addRelation(relation: Relation) {
        getCachedOrLoad(relation).add(relation.targetId!!)
        relationRepository.saveAndFlush(relation)
        relationChangeService.registerChange(relation.createRelationChange(isAppeared = true))
    }

    override fun removeRelation(relation: Relation) {
        getCachedOrLoad(relation).remove(relation.targetId!!)
        relationRepository.deleteByConnectedIdAndTargetIdAndRelationType(
                connectedId = relation.connectedId!!,
                targetId = relation.targetId!!,
                relationType = relation.relationType!!)
        relationChangeService.registerChange(relation.createRelationChange(isAppeared = false))
    }

    override fun addRelations(relations: List<Relation>) {
        relations.forEach { getCachedOrLoad(it).add(it.targetId!!) }
        relationRepository.save(relations)
        relationChangeService.registerChanges(relations.map { it.createRelationChange(isAppeared = true) })
    }

    override fun removeRelations(relations: List<Relation>) {
        relations.forEach { relation ->
            getCachedOrLoad(relation).remove(relation.targetId!!)
            relationRepository.deleteByConnectedIdAndTargetIdAndRelationType(//todo: make batch
                    connectedId = relation.connectedId!!,
                    targetId = relation.targetId!!,
                    relationType = relation.relationType!!)
        }
        relationChangeService.registerChanges(relations.map { it.createRelationChange(isAppeared = false) })
    }

    private fun Relation.createRelationChange(isAppeared: Boolean) = RelationChange(
            time = LocalDateTime.now(),
            connectedId = connectedId,
            targetId = targetId,
            relationType = relationType,
            isAppeared = isAppeared
    )

    private fun getCachedOrLoad(userId: Int, relationType: RelationType): MutableList<Int> {
        val key = "$userId ${relationType.id}"
        if (cache[key] == null) {
            val targets = relationRepository
                    .findAllByConnectedIdAndRelationTypeOrderByTargetId(userId, relationType)
                    .map { it.targetId!! }
            cache[key] = targets
        }
        return cache[key].get() as MutableList<Int>
    }

    private fun getCachedOrLoad(relation: Relation) = getCachedOrLoad(relation.connectedId!!, relation.relationType!!)
}