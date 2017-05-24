package my.maryvkweb.service.impl

import my.maryvkweb.domain.User
import my.maryvkweb.service.UserService
import java.util.concurrent.ConcurrentHashMap

class HashMapUserServiceImpl : UserService {

    private val repository = ConcurrentHashMap<Int, User>()

    override fun exists(userId: Int): Boolean {
        return repository.containsKey(userId)
    }

    override fun find(userId: Int): User? {
        return repository[userId]
    }

    override fun findAll(ids: List<Int>): List<User> {
        return ids.mapNotNull { repository[it] }
    }

    override fun save(user: User) {
        repository[user.id!!] = user
    }

    override fun saveAll(users: Iterable<User>) {
        users.forEach(this::save)
    }
}