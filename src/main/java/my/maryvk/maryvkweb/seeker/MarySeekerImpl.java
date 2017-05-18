package my.maryvk.maryvkweb.seeker;

import lombok.extern.java.Log;
import my.maryvk.maryvkweb.domain.Relation;
import my.maryvk.maryvkweb.domain.RelationType;
import my.maryvk.maryvkweb.service.RelationService;
import my.maryvk.maryvkweb.service.VkService;
import org.apache.commons.collections4.ListUtils;

import java.util.List;

@Log
public class MarySeekerImpl implements MarySeeker {

    private final VkService vk;
    private final int userId;
    private final RelationType relationType;
    private final RelationService relationService;

    public MarySeekerImpl(VkService vk, int userId, RelationType relationType, RelationService relationService) {
        this.vk = vk;
        this.userId = userId;
        this.relationType = relationType;
        this.relationService = relationService;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void seek() {
        List<Integer> wasUsers = getWasUsers();
        List<Integer> curUsers = getCurUsers();

        if (curUsers == null)
            return;

        List<Integer> appeared = ListUtils.removeAll(curUsers, wasUsers);
        List<Integer> disappeared = ListUtils.removeAll(wasUsers, curUsers);

        if (!appeared.isEmpty()) processAppeared(appeared);
        if (!disappeared.isEmpty()) processDisappeared(disappeared);
    }

    private List<Integer> getWasUsers() {
        return relationService.findAllFor(userId, relationType);
    }

    private List<Integer> getCurUsers() {
        return vk.getConnectedIds(userId, relationType);
    }

    private void processAppeared(List<Integer> appeared) {
        appeared.stream()
                .map(this::createRelation)
                .peek(relationService::addRelation)
                .forEach(r -> log.info("New relation appeared: " + r));
    }

    private void processDisappeared(List<Integer> disappeared) {
        disappeared.stream()
                .map(this::createRelation)
                .peek(relationService::removeRelation)
                .forEach(r -> log.info("Relation disappeared: " + r));
    }

    private Relation createRelation(int targetId) {
        return Relation.builder()
                .ownerId(userId)
                .targetId(targetId)
                .relationType(relationType)
                .build();
    }
}