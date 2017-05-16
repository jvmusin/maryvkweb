package my.maryvk.maryvkweb.seeker;

import my.maryvk.maryvkweb.domain.RelationType;
import my.maryvk.maryvkweb.service.RelationService;
import my.maryvk.maryvkweb.service.VkService;

public class FriendsAndFollowersMarySeeker implements MarySeeker {

    private final MarySeeker friendsMarySeeker;
    private final MarySeeker followersMarySeeker;

    private final int usedId;

    public FriendsAndFollowersMarySeeker(VkService vk, int userId, RelationService relationService) {
        friendsMarySeeker = new MarySeekerImpl(vk, userId, RelationType.FRIEND, relationService);
        followersMarySeeker = new MarySeekerImpl(vk, userId, RelationType.FOLLOWER, relationService);
        this.usedId = userId;
    }

    @Override
    public void seek() {
        friendsMarySeeker.seek();
        followersMarySeeker.seek();
    }

    @Override
    public int getUserId() {
        return usedId;
    }
}
