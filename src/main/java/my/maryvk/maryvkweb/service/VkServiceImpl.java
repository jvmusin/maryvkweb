package my.maryvk.maryvkweb.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import lombok.extern.java.Log;
import my.maryvk.maryvkweb.domain.RelationType;
import my.maryvk.maryvkweb.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

@Service("vk")
@Log
public class VkServiceImpl implements VkService {

    private final VkApiClient vk;
    private final UserActor owner;

    private final UserService userService;

    public VkServiceImpl(VkApiClient vk, UserActor owner, UserService userService, @Value("${vk.api-call-delay}") int apiCallRange) {
        this.vk = vk;
        this.owner = owner;
        this.userService = userService;
        this.apiCallRange = apiCallRange;
    }

    @Override
    public List<Integer> getConnectedIds(Integer userId, RelationType relationType) {
        List<Integer> ids = relationType == RelationType.FRIEND ? getFriendIds(userId) : getFollowerIds(userId);
        if (ids != null)
            if (!tryUpdateUsers(ids))
                return null;
        return ids;
    }

    private List<Integer> getFriendIds(Integer userId) {
        try {
            return executeVkApiCall(() -> vk.friends().get(owner).userId(userId).execute().getItems());
        } catch (ApiException | ClientException e) {
            log.warning("Unable to execute friends: " + e.getMessage());
            return null;
        }
    }

    private List<Integer> getFollowerIds(Integer userId) {
        try {
            return executeVkApiCall(() -> vk.users().getFollowers(owner).userId(userId).execute().getItems());
        } catch (ApiException | ClientException e) {
            log.warning("Unable to execute followers: " + e.getMessage());
            return null;
        }
    }

    private boolean tryUpdateUsers(List<Integer> ids) {
        List<Integer> toUpdate = ids.stream()
                .filter(id -> !userService.exists(id))
                .collect(Collectors.toList());
        if (!toUpdate.isEmpty()) {
            List<User> usersFromVk = getUsersFromVk(toUpdate);
            if (usersFromVk == null)
                return false;
            userService.save(usersFromVk);
        }
        return true;
    }

    private List<User> getUsersFromVk(List<Integer> ids) {
        try {
            List<String> idsStr = ids.stream().map(Object::toString).collect(Collectors.toList());
            List<UserXtrCounters> vkUsers = executeVkApiCall(() -> vk.users().get(owner).userIds(idsStr).execute());
            return vkUsers.stream().map(this::createUser).collect(Collectors.toList());
        } catch (ApiException | ClientException e) {
            log.warning("Unable to execute users: " + e.getMessage());
            return null;
        }
    }

    private User createUser(UserXtrCounters u) {
        return User.builder()
                .id(u.getId())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .build();
    }

    private final AtomicLong lastTimeApiUsed = new AtomicLong(0);
    private final Lock lock = new ReentrantLock(true);
    private final int apiCallRange;
    private <T> T executeVkApiCall(VkApiCall<T> fun) throws ApiException, ClientException {
        lock.lock();
        while (currentTimeMillis() - lastTimeApiUsed.get() < apiCallRange);
        try {
            return fun.execute();
        } finally {
            lastTimeApiUsed.set(currentTimeMillis());
            lock.unlock();
        }
    }

    @Override
    public User getUser(Integer id) {
        User user = userService.findOne(id);
        if (user == null) {
            tryUpdateUsers(Collections.singletonList(id));
            user = userService.findOne(id);
        }
        return user;
    }

    private interface VkApiCall<T> {
        T execute() throws ApiException, ClientException;
    }
}