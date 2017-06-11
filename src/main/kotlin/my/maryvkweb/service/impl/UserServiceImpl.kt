package my.maryvkweb.service.impl

import my.maryvkweb.domain.User
import my.maryvkweb.service.repository.UserRepository
import my.maryvkweb.service.UserService
import org.springframework.cache.annotation.Cacheable

open class UserServiceImpl(
        private val userRepository: UserRepository
) : UserService {

    override fun exists(userId: Int) = userRepository.exists(userId)

    @Cacheable(cacheNames = arrayOf("users"), unless = "#result == null")
    override fun find(userId: Int): User? = userRepository.findOne(userId)

    override fun findAll(ids: List<Int>): List<User> = userRepository.findAll(ids)

    override fun save(user: User) {
        userRepository.save(user)
    }

    override fun saveAll(users: Iterable<User>) {
        userRepository.save(users)
    }
}