package my.maryvk.maryvkweb.service;

import my.maryvk.maryvkweb.domain.RelationType;

import java.util.List;

public interface VkService {
    List<Integer> getFriendIds(Integer userId);
    List<Integer> getFollowerIds(Integer userId);

    List<Integer> getConnectedIds(Integer userId, RelationType relationType);
}

