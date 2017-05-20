package my.maryvkweb.service

import my.maryvkweb.LoggerDelegate
import my.maryvkweb.domain.RegisteredSeeker
import my.maryvkweb.repository.RegisteredSeekerRepository
import org.springframework.stereotype.Service

@Service class RegisteredSeekerServiceImpl(
        private val registeredSeekerRepository: RegisteredSeekerRepository
) : RegisteredSeekerService {

    private val log by LoggerDelegate(RegisteredSeekerServiceImpl::class.java)

    override fun register(targetId: Int) {
        val seeker = RegisteredSeeker(targetId = targetId)
        registeredSeekerRepository.saveAndFlush(seeker)
        log.info("Registered new seeker: " + seeker)
    }

    override fun unregister(targetId: Int) {
        val seeker = RegisteredSeeker(targetId = targetId)
        registeredSeekerRepository.deleteByTargetId(targetId)
        log.info("Unregistered seeker: " + seeker)
    }

    override fun findAll(): List<RegisteredSeeker> = registeredSeekerRepository.findAll()
}