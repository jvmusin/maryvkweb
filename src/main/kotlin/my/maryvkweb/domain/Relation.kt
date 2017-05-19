package my.maryvkweb.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity data class Relation(
        @Id @GeneratedValue
        var id: Long? = null,
        var ownerId: Int? = null,
        var targetId: Int? = null,
        var relationType: RelationType? = null
) {
    fun createRelationChange(isAppeared: Boolean) =
            RelationChange(null, LocalDateTime.now(), ownerId, targetId, relationType, isAppeared)
}