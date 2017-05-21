package my.maryvkweb.seeker

import my.maryvkweb.domain.RelationType
import my.maryvkweb.service.RelationService
import my.maryvkweb.service.VkService

class FriendsAndFollowersMarySeeker(
        override val connectedId: Int,
        vk: VkService,
        relationService: RelationService
) : MarySeeker {

    private val friendsMarySeeker: MarySeeker
    private val followersMarySeeker: MarySeeker

    init {
        fun createSeeker(relationType: RelationType) = MarySeekerImpl(
                vk = vk,
                connectedId = connectedId,
                relationType = relationType,
                relationService = relationService
        )
        this.friendsMarySeeker = createSeeker(RelationType.FRIEND)
        this.followersMarySeeker = createSeeker(RelationType.FOLLOWER)
    }

    override fun seek() {
        friendsMarySeeker.seek()
        followersMarySeeker.seek()
    }
}