package my.maryvk.maryvkweb.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity data class Relation(
        @Id @GeneratedValue var id: Long?,
        var ownerId: Int?,
        var targetId: Int?,
        var relationType: RelationType?
) {
    fun createRelationChange(isAppeared: Boolean) =
            RelationChange(null, LocalDateTime.now(), ownerId, targetId, relationType, isAppeared)
}