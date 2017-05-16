package my.maryvk.maryvkweb.repository;

import my.maryvk.maryvkweb.domain.RelationChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationChangeRepository extends JpaRepository<RelationChange, Long> {
    List<RelationChange> findAllByOwnerIdOrderByTimeDesc(Integer ownerId);
    List<RelationChange> findAllByOrderByTimeDesc();
}