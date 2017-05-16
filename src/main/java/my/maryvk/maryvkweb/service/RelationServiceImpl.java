package my.maryvk.maryvkweb.service;

import my.maryvk.maryvkweb.domain.Relation;
import my.maryvk.maryvkweb.domain.RelationType;
import my.maryvk.maryvkweb.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelationServiceImpl implements RelationService {

    private final RelationRepository relationRepository;
    private final RelationChangeService relationChangeService;

    @Autowired
    public RelationServiceImpl(RelationRepository relationRepository, RelationChangeService relationChangeService) {
        this.relationRepository = relationRepository;
        this.relationChangeService = relationChangeService;
    }

    @Override
    public List<Integer> findAllFriends(Integer userId) {
        return findAllFor(userId, RelationType.FRIEND);
    }

    @Override
    public List<Integer> findAllFollowers(Integer userId) {
        return findAllFor(userId, RelationType.FOLLOWER);
    }

    @Override
    public List<Integer> findAllFor(Integer userId, RelationType relationType) {
        return relationRepository.findAllByOwnerIdAndRelationTypeOrderByTargetId(userId, relationType)
                .stream()
                .map(Relation::getTargetId)
                .collect(Collectors.toList());
    }

    @Override
    public void addRelation(Relation relation) {
        relationRepository.saveAndFlush(relation);
        relationChangeService.registerChange(relation.createRelationChange(true));
    }

    @Override
    public void removeRelation(Relation relation) {
        relationRepository.delete(relation);
        relationChangeService.registerChange(relation.createRelationChange(false));
    }
}