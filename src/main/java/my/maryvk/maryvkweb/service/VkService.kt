package my.maryvk.maryvkweb.service

import my.maryvk.maryvkweb.domain.RelationType
import my.maryvk.maryvkweb.domain.User

interface VkService {
    fun getConnectedIds(userId: Int, relationType: RelationType): List<Int>?
    fun getUser(id: Int): User
}