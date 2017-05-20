package my.maryvkweb.seeker

import my.maryvkweb.service.RelationService
import my.maryvkweb.service.VkService
import org.springframework.stereotype.Service

@Service class FriendsAndFollowersMarySeekerFactory(
        private val vk: VkService,
        private val relationService: RelationService
) : MarySeekerFactory {
    override fun create(userId: Int) = FriendsAndFollowersMarySeeker(
            vk = vk,
            userId = userId,
            relationService = relationService
    )
}