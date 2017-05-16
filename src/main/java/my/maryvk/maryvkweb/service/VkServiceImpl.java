package my.maryvk.maryvkweb.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.extern.java.Log;
import my.maryvk.maryvkweb.domain.RelationType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Log
public class VkServiceImpl implements VkService {

    private final VkApiClient vk;
    private final UserActor owner;

    public VkServiceImpl(VkApiClient vk, UserActor owner) {
        this.vk = vk;
        this.owner = owner;
    }

    @Override
    public List<Integer> getConnectedIds(Integer userId, RelationType relationType) {
        return relationType == RelationType.FRIEND ? getFriendIds(userId) : getFollowerIds(userId);
    }

    @Override
    public List<Integer> getFriendIds(Integer userId) {
        try {
            return vk.friends().get(owner).userId(userId).execute().getItems();
        } catch (ApiException | ClientException e) {
            log.warning(() -> "Something bad happened on get friends: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Integer> getFollowerIds(Integer userId) {
        try {
            return vk.users().getFollowers(owner).userId(userId).execute().getItems();
        } catch (ApiException | ClientException e) {
            log.warning(() -> "Something bad happened on get followers: " + e.getMessage());
            return null;
        }
    }
}
