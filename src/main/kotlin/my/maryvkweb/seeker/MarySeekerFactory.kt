package my.maryvkweb.seeker

interface MarySeekerFactory {
    fun create(userId: Int): MarySeeker
}