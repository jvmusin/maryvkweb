package my.maryvkweb.service

import my.maryvkweb.domain.RelationChange
import my.maryvkweb.repository.RelationChangeRepository
import org.springframework.stereotype.Service

@Service class RelationChangeServiceImpl(
        private val relationChangeRepository: RelationChangeRepository
) : RelationChangeService {

    override fun registerChange(relationChange: RelationChange): RelationChange
            = relationChangeRepository.saveAndFlush(relationChange)

    override fun registerChanges(changes: List<RelationChange>) {
        relationChangeRepository.saveAll(changes)
    }

    override fun findAllByConnectedIdOrderByTimeDesc(connectedId: Int): List<RelationChange>
            = relationChangeRepository.findAllByConnectedIdOrderByTimeDesc(connectedId)

    override fun findAllOrderByTimeDesc(): List<RelationChange>
            = relationChangeRepository.findAllByOrderByTimeDesc()
}