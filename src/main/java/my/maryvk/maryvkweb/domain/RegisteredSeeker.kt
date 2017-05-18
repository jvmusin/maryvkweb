package my.maryvk.maryvkweb.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity data class RegisteredSeeker(
        @Id @GeneratedValue var id: Long?,
        var targetId: Int?
) {
    constructor() : this(null, null)
}