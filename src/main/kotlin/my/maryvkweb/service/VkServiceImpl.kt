package my.maryvkweb.service

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.exceptions.ApiException
import com.vk.api.sdk.exceptions.ClientException
import com.vk.api.sdk.objects.users.UserXtrCounters
import my.maryvkweb.VkProperties
import my.maryvkweb.domain.RelationType
import my.maryvkweb.domain.User
import my.maryvkweb.getLogger
import org.springframework.stereotype.Service

@Service("vk") class VkServiceImpl(
        private val vkApiClient: VkApiClient,
        private val userService: UserService,
        private val owner: UserActor,
        private val vkProperties: VkProperties
) : VkService {

    private val log = getLogger<VkServiceImpl>()

    //todo: cache it
    override fun getConnectedIds(userId: Int, relationType: RelationType): List<Int>? {
        val ids = if (relationType === RelationType.FRIEND) getFriendIds(userId) else getFollowerIds(userId)
        if (ids != null)
            if (!tryUpdateUsers(ids))
                return null
        return ids
    }

    private fun getFriendIds(userId: Int): List<Int>? {
        try {
            return vkApiClient.friends().get(owner).userId(userId).execute().items
        } catch (e: ApiException) {
            log.warning("Unable to execute friends: ${e.message}")
            return null
        } catch (e: ClientException) {
            log.warning("Unable to execute friends: ${e.message}")
            return null
        }
    }

    private fun getFollowerIds(userId: Int): List<Int>? {
        try {
            return vkApiClient.users().getFollowers(owner).userId(userId).execute().items
        } catch (e: ApiException) {
            log.warning("Unable to get followers: ${e.message}")
            return null
        } catch (e: ClientException) {
            log.warning("Unable to get followers: ${e.message}")
            return null
        }
    }

    private fun tryUpdateUsers(ids: List<Int>): Boolean {
        val toUpdate = ids.filterNot(userService::exists)
        if (!toUpdate.isEmpty()) {
            val usersFromVk = getUsersFromVk(toUpdate) ?: return false
            userService.saveAll(usersFromVk)
        }
        return true
    }

    private fun getUsersFromVk(ids: List<Int>): List<User>? {
        fun UserXtrCounters.toUser() = User(id = id, firstName = firstName, lastName = lastName)
        try {
            val idsStr = ids.map(Any::toString)
            val vkUsers = vkApiClient.users().get(owner).userIds(idsStr).execute()
            return vkUsers.map(UserXtrCounters::toUser)
        } catch (e: ApiException) {
            log.warning("Unable to get users: ${e.message}")
            return null
        } catch (e: ClientException) {
            log.warning("Unable to get users: ${e.message}")
            return null
        }
    }

    override fun getUser(userId: Int): User? {
        var user = userService.find(userId)
        if (user == null)
            if (tryUpdateUsers(listOf(userId)))
                user = userService.find(userId)
        return user
    }

    override fun authorize(code: String) {
        val userAuthorizationCodeFlow = vkApiClient.oauth()
                .userAuthorizationCodeFlow(vkProperties.clientId, vkProperties.clientSecret, vkProperties.redirectUri, code)
                .execute()
        vkProperties.accessToken = userAuthorizationCodeFlow.accessToken
        vkProperties.ownerId = userAuthorizationCodeFlow.userId
    }
}