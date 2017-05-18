package my.maryvk.maryvkweb.service

import my.maryvk.maryvkweb.domain.RelationChange

interface RelationChangeService {
    fun registerChange(relationChange: RelationChange)

    fun findAllByOwnerIdOrderByTimeDesc(ownerId: Int): List<RelationChange>
    fun findAllOrderByTimeDesc(): List<RelationChange>
}