package my.maryvkweb.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity data class User(
        @Id
        var id: Int? = null,

        var firstName: String? = null,
        var lastName: String? = null
) {
    fun link() = "http://vk.com/id$id"
    fun fullName() = "$firstName $lastName"
    override fun toString() = "$id: ${fullName()}"
}