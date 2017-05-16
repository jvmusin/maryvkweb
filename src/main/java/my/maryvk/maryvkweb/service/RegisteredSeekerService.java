package my.maryvk.maryvkweb.service;

import my.maryvk.maryvkweb.domain.RegisteredSeeker;

import java.util.List;

public interface RegisteredSeekerService {
    void register(Integer targetId);
    void unregister(Integer targetId);
    List<RegisteredSeeker> findAll();
}