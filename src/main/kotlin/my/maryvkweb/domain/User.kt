package my.maryvkweb.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity data class User(
        @Id
        val id: Int? = null,

        val firstName: String? = null,
        val lastName: String? = null
) {
    fun link() = "http://vk.com/id$id"
    fun fullName() = "$firstName $lastName"
    override fun toString() = "$id: ${fullName()}"
}