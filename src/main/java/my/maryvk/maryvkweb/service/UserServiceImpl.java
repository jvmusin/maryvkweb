package my.maryvk.maryvkweb.service;

import my.maryvk.maryvkweb.domain.User;
import my.maryvk.maryvkweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean exists(Integer id) {
        return userRepository.exists(id);
    }

    @Override
    @Cacheable(cacheNames = "users", unless = "#result == null")
    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public void save(Iterable<User> users) {
        userRepository.save(users);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}