package my.maryvkweb.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity data class Relation(
        @Id @GeneratedValue
        val id: Long,

        val connectedId: Int,
        val targetId: Int,

        val relationType: RelationType
)