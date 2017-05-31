package my.maryvkweb.service.impl

import com.vk.api.sdk.client.Lang
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.exceptions.ApiException
import com.vk.api.sdk.exceptions.ClientException
import my.maryvkweb.VkProperties
import my.maryvkweb.domain.RelationType
import my.maryvkweb.domain.User
import my.maryvkweb.service.UserService
import my.maryvkweb.service.VkService
import org.springframework.stereotype.Service

@Service("vk") class VkServiceImpl(
        private val vkApiClient: VkApiClient,
        private val userService: UserService,
        private val owner: UserActor,
        private val vkProperties: VkProperties
) : VkService {

    private val maxQuerySize get() = vkProperties.maxQuerySize

    @Throws(ApiException::class, ClientException::class)
    override fun getConnectedIds(userId: Int, relationType: RelationType): List<Int> {
        val ids =
                if (relationType === RelationType.FRIEND)
                    getFriendIds(userId)
                else
                    getFollowerIds(userId)
        updateUsers(ids)
        return ids
    }

    @Throws(ApiException::class, ClientException::class)
    private fun getFriendIds(userId: Int): List<Int> {
        return getConnectedIds { offset, count ->
            vkApiClient.friends().get(owner).userId(userId)
                    .offset(offset).count(count).execute().items
        }
    }

    @Throws(ApiException::class, ClientException::class)
    private fun getFollowerIds(userId: Int): List<Int> {
        return getConnectedIds { offset, count ->
            vkApiClient.users().getFollowers(owner).userId(userId)
                    .offset(offset).count(count).execute().items
        }
    }

    @Throws(ApiException::class, ClientException::class)
    private fun getConnectedIds(request: (Int, Int) -> List<Int>): List<Int> {
        var lastPackFound = false
        return generateSequence(0, { it + maxQuerySize })
                .takeWhile { !lastPackFound }
                .map { offset -> request(offset, maxQuerySize) }
                .onEach { ids -> lastPackFound = ids.size < maxQuerySize }
                .flatMap { ids -> ids.asSequence() }
                .toList()
    }

    @Throws(ApiException::class, ClientException::class)
    private fun updateUsers(ids: List<Int>) {
        val toUpdate = ids.filterNot(userService::exists)
        if (toUpdate.any()) {
            val usersFromVk = getUsersFromVk(toUpdate)
            userService.saveAll(usersFromVk)
        }
    }

    @Throws(ApiException::class, ClientException::class)
    override fun findUsers(ids: List<Int>): List<User> {
        updateUsers(ids)
        return userService.findAll(ids)
    }

    @Throws(ApiException::class, ClientException::class)
    fun getUsersFromVk(ids: List<Int>): List<User> {
        return (0..ids.size - 1 step maxQuerySize).flatMap { from ->
            val to = Math.min(from + maxQuerySize, ids.size)
            val curIds = ids.subList(from, to).map(Any::toString)
            vkApiClient.users().get(owner)
                    .userIds(curIds).lang(Lang.EN).execute()
                    .map { User(id = it.id, firstName = it.firstName, lastName = it.lastName) }
        }
    }

    @Throws(ApiException::class, ClientException::class)
    override fun findUser(userId: Int): User? {
        updateUsers(listOf(userId))
        return userService.find(userId)
    }

    @Throws(ApiException::class, ClientException::class)
    override fun authorize(code: String) {
        val userAuthorizationCodeFlow = vkApiClient.oauth()
                .userAuthorizationCodeFlow(vkProperties.clientId, vkProperties.clientSecret, vkProperties.redirectUri, code)
                .execute()
        vkProperties.accessToken = userAuthorizationCodeFlow.accessToken
        vkProperties.ownerId = userAuthorizationCodeFlow.userId
    }
}