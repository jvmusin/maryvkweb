package my.maryvkweb.seeker

import my.maryvkweb.domain.Relation
import my.maryvkweb.domain.RelationType
import my.maryvkweb.getLogger
import my.maryvkweb.service.RelationService
import my.maryvkweb.service.VkService

class MarySeekerImpl(
        override val connectedId: Int,
        private val vk: VkService,
        private val relationType: RelationType,
        private val relationService: RelationService
) : MarySeeker {

    private val log = getLogger<MarySeekerImpl>()

    override fun seek() {
        val curUsers = getCurUsers() ?: return
        val wasUsers = getWasUsers()

        val appeared = curUsers.filterNot(wasUsers::contains)
        val disappeared = wasUsers.filterNot(curUsers::contains)

        if (!appeared.isEmpty()) processAppeared(appeared)
        if (!disappeared.isEmpty()) processDisappeared(disappeared)
    }

    private fun getWasUsers() = relationService.findAllFor(connectedId, relationType)
    private fun getCurUsers(): List<Int>? {
        try {
            return vk.getConnectedIds(connectedId, relationType)
        } catch (e: Exception) {
            log.warning("Unable to get current users for $connectedId: $e")
            return null
        }
    }

    private fun processAppeared(appeared: List<Int>) {
        val relations = appeared.map(this::createRelation)
        relationService.addRelations(relations)
        relations.forEach { log.info("New relation appeared: $it") }
    }

    private fun processDisappeared(disappeared: List<Int>) {
        val relations = disappeared.map(this::createRelation)
        relationService.removeRelations(relations)
        relations.forEach { log.info("Relation disappeared: $it") }
    }

    private fun createRelation(targetId: Int) = Relation(
            connectedId = connectedId,
            targetId = targetId,
            relationType = relationType
    )
}