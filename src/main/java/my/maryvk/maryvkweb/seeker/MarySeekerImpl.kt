package my.maryvk.maryvkweb.seeker

import lombok.extern.java.Log
import my.maryvk.maryvkweb.domain.Relation
import my.maryvk.maryvkweb.domain.RelationType
import my.maryvk.maryvkweb.service.RelationService
import my.maryvk.maryvkweb.service.VkService
import org.apache.commons.collections4.ListUtils

@Log
class MarySeekerImpl(private val vk: VkService, override val userId: Int, private val relationType: RelationType, private val relationService: RelationService) : MarySeeker {

    override fun seek() {
        val curUsers = getCurUsers() ?: return
        val wasUsers = getWasUsers()

        val appeared = ListUtils.removeAll(curUsers, wasUsers)
        val disappeared = ListUtils.removeAll(wasUsers, curUsers)

        if (!appeared.isEmpty()) processAppeared(appeared)
        if (!disappeared.isEmpty()) processDisappeared(disappeared)
    }

    private fun getWasUsers(): List<Int> = relationService.findAllFor(userId, relationType)
    private fun getCurUsers(): List<Int>? = vk.getConnectedIds(userId, relationType)

    private fun processAppeared(appeared: List<Int>) {
        appeared.map { createRelation(it) }.forEach {
            relationService.addRelation(it)
//            log.info("New relation appeared: " + it)
        }
    }

    private fun processDisappeared(disappeared: List<Int>) {
        disappeared.map { createRelation(it) }.forEach {
            relationService.removeRelation(it)
//            log.info("Relation disappeared: " + it)
        }
    }

    private fun createRelation(targetId: Int): Relation {
        return Relation(null, userId, targetId, relationType)
    }
}