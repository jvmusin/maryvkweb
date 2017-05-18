package my.maryvk.maryvkweb.service

import my.maryvk.maryvkweb.domain.Relation
import my.maryvk.maryvkweb.domain.RelationType

interface RelationService {
    fun findAllFor(userId: Int, relationType: RelationType): List<Int>

    fun addRelation(relation: Relation)
    fun removeRelation(relation: Relation)
}