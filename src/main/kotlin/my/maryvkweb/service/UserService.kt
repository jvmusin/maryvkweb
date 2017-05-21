package my.maryvkweb.service

import my.maryvkweb.domain.User

interface UserService {
    fun exists(userId: Int): Boolean
    fun find(userId: Int): User?
    fun saveAll(users: Iterable<User>): List<User>
    fun save(user: User): User
}