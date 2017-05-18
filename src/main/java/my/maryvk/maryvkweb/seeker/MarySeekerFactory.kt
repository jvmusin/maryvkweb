package my.maryvk.maryvkweb.seeker

interface MarySeekerFactory {
    fun create(userId: Int): MarySeeker
}