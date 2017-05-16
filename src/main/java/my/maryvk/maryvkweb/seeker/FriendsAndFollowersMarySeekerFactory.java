package my.maryvk.maryvkweb.seeker;

import my.maryvk.maryvkweb.service.RelationService;
import my.maryvk.maryvkweb.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendsAndFollowersMarySeekerFactory implements MarySeekerFactory {

    private final VkService vk;
    private final RelationService relationService;

    @Autowired
    public FriendsAndFollowersMarySeekerFactory(VkService vk, RelationService relationService) {
        this.vk = vk;
        this.relationService = relationService;
    }

    public MarySeeker create(int userId) {
        return new FriendsAndFollowersMarySeeker(vk, userId, relationService);
    }

}