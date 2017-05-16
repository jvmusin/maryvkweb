package my.maryvk.maryvkweb.service;

import my.maryvk.maryvkweb.domain.RelationChange;

import java.util.List;

public interface RelationChangeService {
    void registerChange(RelationChange relationChange);

    List<RelationChange> findAllByOwnerIdOrderByTimeDesc(int ownerId);
    List<RelationChange> findAllOrderByTimeDesc();
}