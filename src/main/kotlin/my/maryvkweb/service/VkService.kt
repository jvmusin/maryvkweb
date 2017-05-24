package my.maryvkweb.service

import my.maryvkweb.domain.RelationType
import my.maryvkweb.domain.User

interface VkService {
    fun getConnectedIds(userId: Int, relationType: RelationType): List<Int>?
    fun findUser(userId: Int): User?
    fun findUsers(ids: List<Int>): List<User>?
    fun authorize(code: String)
}