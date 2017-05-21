package my.maryvkweb.service

import my.maryvkweb.domain.RelationType
import my.maryvkweb.domain.User

interface VkService {
    fun getConnectedIds(userId: Int, relationType: RelationType): List<Int>?
    fun getUser(userId: Int): User?
    fun authorize(code: String)
}