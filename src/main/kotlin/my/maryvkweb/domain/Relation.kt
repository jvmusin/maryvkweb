package my.maryvkweb.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity data class Relation(
        @Id @GeneratedValue
        var id: Long? = null,
        var ownerId: Int? = null,
        var targetId: Int? = null,
        var relationType: RelationType? = null
)