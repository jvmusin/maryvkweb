package my.maryvk.maryvkweb.repository;

import my.maryvk.maryvkweb.domain.RegisteredSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RegisteredSeekerRepository extends JpaRepository<RegisteredSeeker, Long> {
    @Transactional
    void deleteByTargetId(Integer targetId);
}