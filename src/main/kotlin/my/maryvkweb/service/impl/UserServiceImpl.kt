package my.maryvkweb.service.impl

import my.maryvkweb.domain.User
import my.maryvkweb.repository.UserRepository
import my.maryvkweb.service.UserService
import org.springframework.cache.annotation.Cacheable

open class UserServiceImpl(
        private val userRepository: UserRepository
) : UserService {

    override fun exists(userId: Int): Boolean {
        return userRepository.exists(userId)
    }

    @Cacheable(cacheNames = arrayOf("users"), unless = "#result == null")
    override fun find(userId: Int): User? = userRepository.findOne(userId)

    override fun findAll(ids: List<Int>): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(user: User) {
        userRepository.save(user)
    }

    override fun saveAll(users: Iterable<User>) {
        userRepository.save(users)
    }
}