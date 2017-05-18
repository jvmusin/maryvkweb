package my.maryvk.maryvkweb.service;

import my.maryvk.maryvkweb.domain.User;

import java.util.List;

public interface UserService {
    boolean exists(Integer id);
    User findOne(Integer id);
    void save(Iterable<User> users);
    void save(User user);
}