package my.maryvkweb.domain

enum class RelationType(val id: Int) {
    FRIEND(0),
    FOLLOWER(1);

    companion object Factory {
        fun get(id: Int) = if (id == 0) FRIEND else FOLLOWER
    }
}