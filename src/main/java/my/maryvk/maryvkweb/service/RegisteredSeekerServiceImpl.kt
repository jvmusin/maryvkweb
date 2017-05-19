package my.maryvk.maryvkweb.service

import my.maryvk.maryvkweb.domain.RegisteredSeeker
import my.maryvk.maryvkweb.repository.RegisteredSeekerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service class RegisteredSeekerServiceImpl(
        private val registeredSeekerRepository: RegisteredSeekerRepository
) : RegisteredSeekerService {

    override fun register(targetId: Int) {
        val seeker = RegisteredSeeker(targetId = targetId)
        registeredSeekerRepository.saveAndFlush(seeker)
//        log.info("Registered new seeker: " + seeker)
    }

    override fun unregister(targetId: Int) {
//        val seeker = RegisteredSeeker(targetId = targetId)
        registeredSeekerRepository.deleteByTargetId(targetId)
//        log.info("Unregistered seeker: " + seeker)
    }

    override fun findAll(): List<RegisteredSeeker> {
        return registeredSeekerRepository.findAll()
    }
}