package my.maryvkweb.service

import my.maryvkweb.domain.User
import my.maryvkweb.repository.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service open class UserServiceImpl(
        private val userRepository: UserRepository
) : UserService {

    override fun exists(id: Int): Boolean {
        return userRepository.existsById(id)
    }

    @Cacheable(cacheNames = arrayOf("users"), unless = "#result == null")
    override fun findOne(id: Int): User? {
        return userRepository.findById(id).orElse(null)
    }

    override fun saveAll(users: Iterable<User>) {
        userRepository.saveAll(users)
    }

    override fun save(user: User) {
        userRepository.save(user)
    }
}