package my.maryvk.maryvkweb.service

import my.maryvk.maryvkweb.domain.User

interface UserService {
    fun exists(id: Int): Boolean
    fun findOne(id: Int): User
    fun save(users: Iterable<User>)
    fun save(user: User)
}