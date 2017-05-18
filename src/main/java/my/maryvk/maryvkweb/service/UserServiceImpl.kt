package my.maryvk.maryvkweb.service

import my.maryvk.maryvkweb.domain.User
import my.maryvk.maryvkweb.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class UserServiceImpl
@Autowired constructor(private val userRepository: UserRepository) : UserService {

    override fun exists(id: Int): Boolean {
        return userRepository.exists(id)
    }

    @Cacheable(cacheNames = arrayOf("users"), unless = "#result == null")
    override fun findOne(id: Int): User {
        return userRepository.findOne(id)
    }

    override fun save(users: Iterable<User>) {
        userRepository.save(users)
    }

    override fun save(user: User) {
        userRepository.save(user)
    }
}