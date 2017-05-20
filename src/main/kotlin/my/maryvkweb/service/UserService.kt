package my.maryvkweb.service

import my.maryvkweb.domain.User

interface UserService {
    fun exists(id: Int): Boolean
    fun findOne(id: Int): User?
    fun saveAll(users: Iterable<User>): List<User>
    fun save(user: User): User
}