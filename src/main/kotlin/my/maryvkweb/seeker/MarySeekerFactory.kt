package my.maryvkweb.seeker

interface MarySeekerFactory {
    fun create(connectedId: Int): MarySeeker
}