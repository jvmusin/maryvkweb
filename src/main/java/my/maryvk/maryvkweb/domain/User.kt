package my.maryvk.maryvkweb.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity data class User(
        @Id val id: Int? = null,
        val firstName: String? = null,
        val lastName: String? = null
) {
    fun link(): String = "http://vk.com/id" + id
    fun fullName(): String = "$firstName $lastName"
    override fun toString(): String = "$id: $firstName $lastName"
}