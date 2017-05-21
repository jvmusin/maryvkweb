package my.maryvkweb.seeker

import my.maryvkweb.service.RelationService
import my.maryvkweb.service.VkService
import org.springframework.stereotype.Service

@Service class FriendsAndFollowersMarySeekerFactory(
        private val vkService: VkService,
        private val relationService: RelationService
) : MarySeekerFactory {
    override fun create(connectedId: Int) = FriendsAndFollowersMarySeeker(
            connectedId = connectedId,
            vk = vkService,
            relationService = relationService
    )
}