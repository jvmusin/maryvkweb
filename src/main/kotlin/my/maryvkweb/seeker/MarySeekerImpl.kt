package my.maryvkweb.seeker

import my.maryvkweb.domain.Relation
import my.maryvkweb.domain.RelationType
import my.maryvkweb.service.RelationService
import my.maryvkweb.service.VkService
import org.apache.commons.collections4.ListUtils
import java.util.logging.Logger

class MarySeekerImpl(
        private val vk: VkService,
        override val userId: Int,
        private val relationType: RelationType,
        private val relationService: RelationService
) : MarySeeker {

    companion object {
        private val log = Logger.getLogger(MarySeekerImpl.toString())
    }

    override fun seek() {
        val curUsers = getCurUsers() ?: return
        val wasUsers = getWasUsers()

        val appeared = ListUtils.removeAll(curUsers, wasUsers)
        val disappeared = ListUtils.removeAll(wasUsers, curUsers)

        if (!appeared.isEmpty()) processAppeared(appeared)
        if (!disappeared.isEmpty()) processDisappeared(disappeared)
    }

    private fun getWasUsers() = relationService.findAllFor(userId, relationType)
    private fun getCurUsers() = vk.getConnectedIds(userId, relationType)

    private fun processAppeared(appeared: List<Int>) {
        appeared.map { createRelation(it) }.forEach {
            relationService.addRelation(it)
            log.info("New relation appeared: " + it)
        }
    }

    private fun processDisappeared(disappeared: List<Int>) {
        disappeared.map { createRelation(it) }.forEach {
            relationService.removeRelation(it)
            log.info("Relation disappeared: " + it)
        }
    }

    private fun createRelation(targetId: Int): Relation {
        return Relation(
                id = null,
                ownerId = userId,
                targetId = targetId,
                relationType = relationType)
    }
}