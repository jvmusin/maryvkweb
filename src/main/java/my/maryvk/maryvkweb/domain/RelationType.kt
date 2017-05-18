package my.maryvk.maryvkweb.domain

enum class RelationType(val id: kotlin.Int) {
    FRIEND(0),
    FOLLOWER(1);

    companion object Factory {
        fun get(id: Int) = if (id == 0) FRIEND else FOLLOWER
    }
}