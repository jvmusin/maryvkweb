package my.maryvkweb.service.impl

import my.maryvkweb.domain.RelationChange
import my.maryvkweb.repository.RelationChangeRepository
import my.maryvkweb.service.RelationChangeService
import org.springframework.stereotype.Service

@Service class RelationChangeServiceImpl(
        private val relationChangeRepository: RelationChangeRepository
) : RelationChangeService {

    private val relationChanges: MutableList<RelationChange> by lazy { relationChangeRepository.findAll() }

    override fun registerChange(relationChange: RelationChange): RelationChange {
        val result = relationChangeRepository.saveAndFlush(relationChange)
        relationChanges.add(result)
        return result
    }

    override fun registerChanges(changes: List<RelationChange>) {
        relationChangeRepository.saveAll(changes)
        relationChanges.addAll(changes)
    }

    override fun findAllByConnectedIdOrderByTimeDesc(connectedId: Int): List<RelationChange>
            = relationChanges.asSequence()
            .filter { it.connectedId == connectedId }
            .sortedWith(reverseOrder())
            .toList()

    override fun findAllOrderByTimeDesc(): List<RelationChange>
            = relationChanges.asSequence()
            .sortedWith(reverseOrder())
            .toList()
}