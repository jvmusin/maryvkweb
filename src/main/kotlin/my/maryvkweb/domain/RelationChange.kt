package my.maryvkweb.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity data class RelationChange (
        @Id @GeneratedValue
        val id: Long,

        val time: LocalDateTime,
        val connectedId: Int,
        val targetId: Int,

        val relationType: RelationType,
        val isAppeared: Boolean
): Comparable<RelationChange> {

    companion object {
        val comparator: Comparator<RelationChange> = Comparator
                .comparing(RelationChange::time)
                .thenComparingLong(RelationChange::id)
    }

    override fun compareTo(other: RelationChange): Int {
        return comparator.compare(this, other)
    }
}