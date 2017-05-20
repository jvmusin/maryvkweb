package my.maryvkweb.service

import my.maryvkweb.domain.Relation
import my.maryvkweb.domain.RelationChange
import my.maryvkweb.domain.RelationType

interface RelationService {
    fun findAllFor(userId: Int, relationType: RelationType): List<Int>

    fun addRelation(relation: Relation)
    fun removeRelation(relation: Relation)
}