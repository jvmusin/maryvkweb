package my.maryvkweb.service

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.exceptions.ApiException
import com.vk.api.sdk.exceptions.ClientException
import my.maryvkweb.domain.RelationType
import my.maryvkweb.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.lang.System.currentTimeMillis
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.ReentrantLock
import java.util.logging.Logger

@Service("vk") class VkServiceImpl(
        private val vk: VkApiClient,
        private val owner: UserActor,
        private val userService: UserService,
        @Value("\${vk.api-call-delay}")
        private val apiCallRange: Int
) : VkService {

    companion object {
        private val log = Logger.getLogger(VkServiceImpl.toString())
    }

    override fun getConnectedIds(userId: Int, relationType: RelationType): List<Int>? {
        val ids = if (relationType === RelationType.FRIEND) getFriendIds(userId) else getFollowerIds(userId)
        if (ids != null)
            if (!tryUpdateUsers(ids))
                return null
        return ids
    }

    private fun getFriendIds(userId: Int?): List<Int>? {
        try {
            return executeVkApiCall { vk.friends().get(owner).userId(userId!!).execute().items }
        } catch (e: ApiException) {
            log.warning("Unable to execute friends: ${e.message}")
            return null
        } catch (e: ClientException) {
            log.warning("Unable to execute friends: ${e.message}")
            return null
        }

    }

    private fun getFollowerIds(userId: Int?): List<Int>? {
        try {
            return executeVkApiCall { vk.users().getFollowers(owner).userId(userId!!).execute().items }
        } catch (e: ApiException) {
            log.warning("Unable to get followers: ${e.message}")
            return null
        } catch (e: ClientException) {
            log.warning("Unable to get followers: ${e.message}")
            return null
        }
    }

    private fun tryUpdateUsers(ids: List<Int>): Boolean {
        val toUpdate = ids.filter { id -> !userService.exists(id) }
        if (!toUpdate.isEmpty()) {
            val usersFromVk = getUsersFromVk(toUpdate) ?: return false
            userService.saveAll(usersFromVk)
        }
        return true
    }

    private fun getUsersFromVk(ids: List<Int>): List<User>? {
        try {
            val idsStr = ids.map(Any::toString).toList()
            val vkUsers = executeVkApiCall { vk.users().get(owner).userIds(idsStr).execute() }
            return vkUsers.map { User(id = it.id, firstName = it.firstName, lastName = it.lastName) }.toList()
        } catch (e: ApiException) {
            log.warning("Unable to get users: ${e.message}")
            return null
        } catch (e: ClientException) {
            log.warning("Unable to get users: ${e.message}")
            return null
        }
    }

    private val lastTimeApiUsed = AtomicLong(0)
    private val lock = ReentrantLock(true)
    @Throws(ApiException::class, ClientException::class)
    private fun <T> executeVkApiCall(func: () -> T): T {
        lock.lock()
        while (currentTimeMillis() - lastTimeApiUsed.get() < apiCallRange);
        try {
            return func()
        } finally {
            lastTimeApiUsed.set(currentTimeMillis())
            lock.unlock()
        }
    }

    override fun getUser(id: Int): User {
        var user = userService.findOne(id)
        if (user == null) {
            tryUpdateUsers(listOf(id))
            user = userService.findOne(id)
        }
        return user!!
    }
}