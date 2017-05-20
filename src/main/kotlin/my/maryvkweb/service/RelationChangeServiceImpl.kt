package my.maryvkweb.service

import my.maryvkweb.domain.RelationChange
import my.maryvkweb.repository.RelationChangeRepository
import org.springframework.stereotype.Service

@Service class RelationChangeServiceImpl(
        private val relationChangeRepository: RelationChangeRepository
) : RelationChangeService {

    override fun registerChange(relationChange: RelationChange): RelationChange
            = relationChangeRepository.saveAndFlush(relationChange)

    override fun findAllByOwnerIdOrderByTimeDesc(ownerId: Int): List<RelationChange>
            = relationChangeRepository.findAllByOwnerIdOrderByTimeDesc(ownerId)

    override fun findAllOrderByTimeDesc(): List<RelationChange>
            = relationChangeRepository.findAllByOrderByTimeDesc()
}