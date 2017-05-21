package my.maryvkweb.service

import my.maryvkweb.domain.Relation
import my.maryvkweb.domain.RelationChange
import my.maryvkweb.domain.RelationType
import my.maryvkweb.repository.RelationRepository
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service class RelationServiceImpl(
        private val relationRepository: RelationRepository,
        private val relationChangeService: RelationChangeService,
        cacheManager: CacheManager
) : RelationService {

//    private val cache: Cache = cacheManager.getCache("relations")

    override fun findAllFor(userId: Int, relationType: RelationType): List<Int> {
//        val cacheKey = cacheKey(userId, relationType)
//        val wrapper = cache.get(cacheKey)
//        var result = wrapper?.get()
//        if (result == null) {
//            result = findAllWithQuery(userId, relationType)
//            cache.put(cacheKey, result)
//        }
//        return result as List<Int>
        return findAllWithQuery(userId, relationType)
    }
    private fun findAllWithQuery(userId: Int, relationType: RelationType) = relationRepository
            .findAllByConnectedIdAndRelationTypeOrderByTargetId(userId, relationType)
            .map { it.targetId!! }

    override fun addRelation(relation: Relation) {
//        cache.evict(relation.cacheKey())
        relationRepository.saveAndFlush(relation)
        relationChangeService.registerChange(relation.createRelationChange(isAppeared = true))
    }

    override fun removeRelation(relation: Relation) {
//        cache.evict(relation.cacheKey())
        relationRepository.deleteByConnectedIdAndTargetIdAndRelationType(relation.connectedId!!, relation.targetId!!, relation.relationType!!)
        relationChangeService.registerChange(relation.createRelationChange(isAppeared = false))
    }

    override fun addRelations(relations: List<Relation>) {
//        relations.forEach { cache.evict(it.cacheKey()) }
        relationRepository.saveAll(relations)
        relationChangeService.registerChanges(relations.map { it.createRelationChange(isAppeared = true) })
    }

    override fun removeRelations(relations: List<Relation>) {
//        relations.forEach { cache.evict(it.cacheKey()) }
        relations.forEach { relationRepository.saveAndFlush(it) }   //todo: fix it
        relationChangeService.registerChanges(relations.map { it.createRelationChange(isAppeared = false) })
    }

    private fun Relation.createRelationChange(isAppeared: Boolean) = RelationChange(
            time = LocalDateTime.now(),
            connectedId = connectedId,
            targetId = targetId,
            relationType = relationType,
            isAppeared = isAppeared
    )

    private fun cacheKey(userId: Int, relationType:RelationType) = "$userId ${relationType.id}"
    private fun Relation.cacheKey() = cacheKey(connectedId!!, relationType!!)
}