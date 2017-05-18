package my.maryvk.maryvkweb.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity data class User(
        @Id val id: Int = 0,
        val firstName: String = "",
        val lastName: String = ""
) {
    fun link(): String = "http://vk.com/id" + id
    fun fullName(): String = "$firstName $lastName"
    override fun toString(): String = "$id: $firstName $lastName"
}