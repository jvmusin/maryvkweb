package my.maryvkweb.service

import my.maryvkweb.domain.User
import java.util.concurrent.ConcurrentHashMap

class HashMapUserServiceImpl : UserService {

    private val repository = ConcurrentHashMap<Int, User>()

    override fun exists(userId: Int): Boolean {
        return repository.containsKey(userId)
    }

    override fun find(userId: Int): User? {
        return repository[userId]
    }

    override fun save(user: User) {
        repository[user.id!!] = user
    }

    override fun saveAll(users: Iterable<User>) {
        users.forEach(this::save)
    }
}