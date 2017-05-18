package my.maryvk.maryvkweb.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity data class RelationChange(
        @Id @GeneratedValue var id: Long?,

        var time: LocalDateTime?,
        var ownerId: Int?,
        var targetId: Int?,

        var relationType: RelationType?,
        var isAppeared: Boolean?
) {
    constructor() : this(null, null, null, null, null, null)
}