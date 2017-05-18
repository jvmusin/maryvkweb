package my.maryvk.maryvkweb.service

import my.maryvk.maryvkweb.domain.RelationChange
import my.maryvk.maryvkweb.repository.RelationChangeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RelationChangeServiceImpl
@Autowired constructor(private val relationChangeRepository: RelationChangeRepository) : RelationChangeService {

    override fun registerChange(relationChange: RelationChange) {
        relationChangeRepository.saveAndFlush(relationChange)
    }

    override fun findAllByOwnerIdOrderByTimeDesc(ownerId: Int): List<RelationChange> {
        return relationChangeRepository.findAllByOwnerIdOrderByTimeDesc(ownerId)
    }

    override fun findAllOrderByTimeDesc(): List<RelationChange> {
        return relationChangeRepository.findAllByOrderByTimeDesc()
    }
}