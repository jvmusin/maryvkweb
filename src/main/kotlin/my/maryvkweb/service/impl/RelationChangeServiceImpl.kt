package my.maryvkweb.service.impl

import my.maryvkweb.domain.RelationChange
import my.maryvkweb.service.repository.RelationChangeRepository
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
        relationChangeRepository.save(changes)
        relationChanges.addAll(changes)
    }

    override fun findAllByConnectedId(connectedId: Int)
            = relationChanges
            .filter { it.connectedId == connectedId }

    override fun findAllByConnectedIdOrderByIdDesc(connectedId: Int)
            = relationChanges
            .filter { it.connectedId == connectedId }
            .sortedByDescending { it.id }

    override fun findAllOrderByIdDesc()
            = relationChanges
            .sortedByDescending { it.id!! }

}