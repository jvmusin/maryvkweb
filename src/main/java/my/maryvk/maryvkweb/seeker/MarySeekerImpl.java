package my.maryvk.maryvkweb.seeker;

import my.maryvk.maryvkweb.domain.Relation;
import my.maryvk.maryvkweb.domain.RelationType;
import my.maryvk.maryvkweb.service.RelationService;
import my.maryvk.maryvkweb.service.VkService;

import java.util.List;
import java.util.stream.Collectors;

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

        List<Integer> appeared = getOnlyAppeared(wasUsers, curUsers);
        List<Integer> disappeared = getOnlyAppeared(curUsers, wasUsers);

        if (!appeared.isEmpty()) processAppeared(appeared);
        if (!disappeared.isEmpty()) processDisappeared(disappeared);
    }

    private List<Integer> getOnlyAppeared(List<Integer> source, List<Integer> toCheck) {
        return toCheck.stream()
                .filter(x -> !source.contains(x))
                .collect(Collectors.toList());
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
                .forEach(relationService::addRelation);
        //todo: add logging
    }

    private void processDisappeared(List<Integer> disappeared) {
        disappeared.stream()
                .map(this::createRelation)
                .forEach(relationService::removeRelation);
        //todo: add logging
    }

    private Relation createRelation(int targetId) {
        return Relation.builder()
                .ownerId(userId)
                .targetId(targetId)
                .relationType(relationType)
                .build();
    }
}