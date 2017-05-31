package my.maryvkweb.service.impl

import my.maryvkweb.domain.RegisteredSeeker
import my.maryvkweb.getLogger
import my.maryvkweb.repository.RegisteredSeekerRepository
import my.maryvkweb.service.RegisteredSeekerService
import org.springframework.stereotype.Service

@Service class RegisteredSeekerServiceImpl(
        private val registeredSeekerRepository: RegisteredSeekerRepository
) : RegisteredSeekerService {

    private val log = getLogger<RegisteredSeekerServiceImpl>()

    private val registeredSeekers: MutableMap<Int, RegisteredSeeker> by lazy {
        @Suppress("UNCHECKED_CAST")
        registeredSeekerRepository.findAll().associateBy { it.connectedId } as MutableMap<Int, RegisteredSeeker>
    }

    override fun register(connectedId: Int) {
        val seeker = RegisteredSeeker(id = -1, connectedId = connectedId)
        registeredSeekerRepository.saveAndFlush(seeker)
        registeredSeekers.put(connectedId, seeker)

        log.info("Registered new seeker: " + seeker)
    }

    override fun unregister(connectedId: Int) {
        registeredSeekers.remove(connectedId)
        registeredSeekerRepository.deleteByConnectedId(connectedId)

        val seeker = RegisteredSeeker(connectedId = connectedId, id = -1)
        log.info("Unregistered seeker: " + seeker)
    }

    override fun findAll(): List<RegisteredSeeker> = registeredSeekers.values.toList()
}