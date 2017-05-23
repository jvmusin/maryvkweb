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

    override fun register(connectedId: Int) {
        val seeker = RegisteredSeeker(connectedId = connectedId)
        registeredSeekerRepository.saveAndFlush(seeker)
        log.info("Registered new seeker: " + seeker)
    }

    override fun unregister(connectedId: Int) {
        val seeker = RegisteredSeeker(connectedId = connectedId)
        registeredSeekerRepository.deleteByConnectedId(connectedId)
        log.info("Unregistered seeker: " + seeker)
    }

    override fun findAll(): List<RegisteredSeeker> = registeredSeekerRepository.findAll()
}