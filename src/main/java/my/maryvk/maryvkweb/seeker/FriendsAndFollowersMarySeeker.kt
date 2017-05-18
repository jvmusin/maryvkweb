package my.maryvk.maryvkweb.seeker

import my.maryvk.maryvkweb.domain.RelationType
import my.maryvk.maryvkweb.service.RelationService
import my.maryvk.maryvkweb.service.VkService

class FriendsAndFollowersMarySeeker(vk: VkService, override val userId: Int, relationService: RelationService) : MarySeeker {

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