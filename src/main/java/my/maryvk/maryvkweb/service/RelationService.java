package my.maryvk.maryvkweb.service;

import my.maryvk.maryvkweb.domain.Relation;
import my.maryvk.maryvkweb.domain.RelationType;

import java.util.List;

public interface RelationService {
    List<Integer> findAllFriends(Integer userId);
    List<Integer> findAllFollowers(Integer userId);
    List<Integer> findAllFor(Integer userId, RelationType relationType);

    void addRelation(Relation relation);
    void removeRelation(Relation relation);
}