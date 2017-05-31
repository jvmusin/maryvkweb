package my.maryvkweb.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity data class RegisteredSeeker(
        @Id @GeneratedValue
        val id: Long,

        val connectedId: Int
)