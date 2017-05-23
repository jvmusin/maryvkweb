package my.maryvkweb.service

import my.maryvkweb.domain.User
import my.maryvkweb.repository.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

open class UserServiceImpl(
        private val userRepository: UserRepository
) : UserService {

    override fun exists(userId: Int): Boolean {
        return userRepository.findById(userId).isPresent
    }

    @Cacheable(cacheNames = arrayOf("users"), unless = "#result == null")
    override fun find(userId: Int): User? = userRepository.findById(userId).orElse(null)

    override fun save(user: User) {
        userRepository.save(user)
    }

    override fun saveAll(users: Iterable<User>) {
        userRepository.saveAll(users)
    }
}