package my.maryvkweb.service

import my.maryvkweb.domain.RelationChange

interface RelationChangeService {
    fun registerChange(relationChange: RelationChange): RelationChange
    fun registerChanges(changes: List<RelationChange>)

    fun findAllByConnectedId(connectedId: Int): List<RelationChange>
    fun findAllByConnectedIdOrderByIdDesc(connectedId: Int): List<RelationChange>
    fun findAllOrderByIdDesc(): List<RelationChange>
}