package my.maryvkweb.service

import my.maryvkweb.domain.RelationChange

interface RelationChangeService {
    fun registerChange(relationChange: RelationChange): RelationChange
    fun registerChanges(changes: List<RelationChange>)

    fun findAllByConnectedId(connectedId: Int): List<RelationChange>
    fun findAllSortedByIdDesc(): List<RelationChange>
}