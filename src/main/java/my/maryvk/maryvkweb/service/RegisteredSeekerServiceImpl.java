package my.maryvk.maryvkweb.service;

import lombok.extern.java.Log;
import my.maryvk.maryvkweb.domain.RegisteredSeeker;
import my.maryvk.maryvkweb.repository.RegisteredSeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class RegisteredSeekerServiceImpl implements RegisteredSeekerService {

    private final RegisteredSeekerRepository registeredSeekerRepository;
    private final VkService vk;

    @Autowired
    public RegisteredSeekerServiceImpl(RegisteredSeekerRepository registeredSeekerRepository, VkService vk) {
        this.registeredSeekerRepository = registeredSeekerRepository;
        this.vk = vk;
    }

    @Override
    public void register(Integer targetId) {
        RegisteredSeeker seeker = create(targetId);
        registeredSeekerRepository.saveAndFlush(seeker);
        vk.getUser(targetId);   //pull user info
        log.info("Registered new seeker: " + seeker);
    }

    @Override
    public void unregister(Integer targetId) {
        RegisteredSeeker seeker = create(targetId);
        registeredSeekerRepository.deleteByTargetId(targetId);
        log.info("Unregistered seeker: " + seeker);
    }

    private RegisteredSeeker create(Integer targetId) {
        return RegisteredSeeker.builder().targetId(targetId).build();
    }

    @Override
    public List<RegisteredSeeker> findAll() {
        return registeredSeekerRepository.findAll();
    }
}