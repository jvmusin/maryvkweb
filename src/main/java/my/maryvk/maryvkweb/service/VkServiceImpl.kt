package my.maryvk.maryvkweb.service

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.exceptions.ApiException
import com.vk.api.sdk.exceptions.ClientException
import com.vk.api.sdk.objects.users.UserXtrCounters
import my.maryvk.maryvkweb.domain.RelationType
import my.maryvk.maryvkweb.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.lang.System.currentTimeMillis
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.ReentrantLock

@Service("vk") class VkServiceImpl(
        private val vk: VkApiClient,
        private val owner: UserActor,
        private val userService: UserService,
        @Value("\${vk.api-call-delay}")
        private val apiCallRange: Int
) : VkService {

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
//            log.warning("Unable to execute friends: " + e.message)
            return null
        } catch (e: ClientException) {
//            log.warning("Unable to execute friends: " + e.message)
            return null
        }

    }

    private fun getFollowerIds(userId: Int?): List<Int>? {
        try {
            return executeVkApiCall { vk.users().getFollowers(owner).userId(userId!!).execute().items }
        } catch (e: ApiException) {
//            log.warning("Unable to execute followers: " + e.message)
            return null
        } catch (e: ClientException) {
//            log.warning("Unable to execute followers: " + e.message)
            return null
        }

    }

    private fun tryUpdateUsers(ids: List<Int>): Boolean {
        val toUpdate = ids
                .filter { id -> !userService.exists(id) }
                .toList()
        if (!toUpdate.isEmpty()) {
            val usersFromVk = getUsersFromVk(toUpdate) ?: return false
            userService.save(usersFromVk)
        }
        return true
    }

    private fun getUsersFromVk(ids: List<Int>): List<User>? {
        try {
            val idsStr = ids.map { it.toString() }.toList()
            val vkUsers = executeVkApiCall { vk.users().get(owner).userIds(idsStr).execute() }
            return vkUsers.map { createUser(it) }.toList()
        } catch (e: ApiException) {
//            log.warning("Unable to execute users: " + e.message)
            return null
        } catch (e: ClientException) {
//            log.warning("Unable to execute users: " + e.message)
            return null
        }

    }

    private fun createUser(u: UserXtrCounters): User {
        return User(id = u.id, firstName = u.firstName, lastName = u.lastName)
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
        var user: User? = userService.findOne(id)
        if (user == null) {
            tryUpdateUsers(listOf(id))
            user = userService.findOne(id)
        }
        return user
    }
}