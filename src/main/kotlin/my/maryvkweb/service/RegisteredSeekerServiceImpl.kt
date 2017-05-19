package my.maryvkweb.service

import my.maryvkweb.domain.RegisteredSeeker
import my.maryvkweb.repository.RegisteredSeekerRepository
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service class RegisteredSeekerServiceImpl(
        private val registeredSeekerRepository: RegisteredSeekerRepository
) : RegisteredSeekerService {

    companion object {
        private val log = Logger.getLogger(RegisteredSeekerServiceImpl.toString())
    }

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

    override fun findAll(): List<RegisteredSeeker> {
        return registeredSeekerRepository.findAll()
    }
}