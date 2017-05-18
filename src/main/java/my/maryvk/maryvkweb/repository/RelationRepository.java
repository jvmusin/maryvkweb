package my.maryvk.maryvkweb.repository;

import my.maryvk.maryvkweb.domain.Relation;
import my.maryvk.maryvkweb.domain.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RelationRepository extends JpaRepository<Relation, Long> {
    List<Relation> findAllByOwnerIdAndRelationTypeOrderByTargetId(Integer ownerId, RelationType relationType);
    @Transactional
    void deleteByOwnerIdAndTargetIdAndRelationType(Integer ownerId, Integer targetId, RelationType relationType);
}