package my.maryvk.maryvkweb.seeker

import my.maryvk.maryvkweb.service.RelationService
import my.maryvk.maryvkweb.service.VkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FriendsAndFollowersMarySeekerFactory(
        private val vk: VkService,
        private val relationService: RelationService
) : MarySeekerFactory {
    override fun create(userId: Int): MarySeeker {
        return FriendsAndFollowersMarySeeker(vk = vk, userId = userId, relationService = relationService)
    }
}