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

    private val registeredSeekers: MutableList<RegisteredSeeker> by lazy { ArrayList<RegisteredSeeker>() }

    override fun register(connectedId: Int) {
        val seeker = RegisteredSeeker(connectedId = connectedId)
        registeredSeekerRepository.saveAndFlush(seeker)
        registeredSeekers.add(seeker)

        log.info("Registered new seeker: " + seeker)
    }

    override fun unregister(connectedId: Int) {
        registeredSeekers.removeIf { it.connectedId == connectedId }
        registeredSeekerRepository.deleteByConnectedId(connectedId)

        val seeker = RegisteredSeeker(connectedId = connectedId)
        log.info("Unregistered seeker: " + seeker)
    }

    override fun findAll(): List<RegisteredSeeker> = registeredSeekers
}