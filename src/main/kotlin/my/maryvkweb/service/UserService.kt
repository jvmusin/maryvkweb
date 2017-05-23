package my.maryvkweb.service

import my.maryvkweb.domain.User

interface UserService {
    fun exists(userId: Int): Boolean
    fun find(userId: Int): User?
    fun save(user: User)
    fun saveAll(users: Iterable<User>)
}