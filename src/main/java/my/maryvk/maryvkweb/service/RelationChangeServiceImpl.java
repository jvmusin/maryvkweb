package my.maryvk.maryvkweb.service;

import lombok.extern.java.Log;
import my.maryvk.maryvkweb.domain.RelationChange;
import my.maryvk.maryvkweb.repository.RelationChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class RelationChangeServiceImpl implements RelationChangeService {

    private final RelationChangeRepository relationChangeRepository;

    @Autowired
    public RelationChangeServiceImpl(RelationChangeRepository relationChangeRepository) {
        this.relationChangeRepository = relationChangeRepository;
    }

    @Override
    public void registerChange(RelationChange relationChange) {
        relationChangeRepository.saveAndFlush(relationChange);
    }

    @Override
    public List<RelationChange> findAllByOwnerIdOrderByTimeDesc(int ownerId) {
        return relationChangeRepository.findAllByOwnerIdOrderByTimeDesc(ownerId);
    }

    @Override
    public List<RelationChange> findAllOrderByTimeDesc() {
        return relationChangeRepository.findAllByOrderByTimeDesc();
    }
}