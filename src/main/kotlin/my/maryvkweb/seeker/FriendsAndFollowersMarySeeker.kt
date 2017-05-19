package my.maryvkweb.seeker

import my.maryvkweb.domain.RelationType
import my.maryvkweb.service.RelationService
import my.maryvkweb.service.VkService

class FriendsAndFollowersMarySeeker(
        vk: VkService,
        override val userId: Int,
        relationService: RelationService
) : MarySeeker {

    private val friendsMarySeeker: MarySeeker
    private val followersMarySeeker: MarySeeker

    init {
        this.friendsMarySeeker = MarySeekerImpl(vk = vk, userId = userId, relationType = RelationType.FRIEND, relationService = relationService)
        this.followersMarySeeker = MarySeekerImpl(vk = vk, userId = userId, relationType = RelationType.FOLLOWER, relationService = relationService)
    }

    override fun seek() {
        friendsMarySeeker.seek()
        followersMarySeeker.seek()
    }
}