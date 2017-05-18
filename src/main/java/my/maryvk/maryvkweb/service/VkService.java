package my.maryvk.maryvkweb.service;

import my.maryvk.maryvkweb.domain.RelationType;
import my.maryvk.maryvkweb.domain.User;

import java.util.List;

public interface VkService {
    List<Integer> getConnectedIds(Integer userId, RelationType relationType);
    User getUser(Integer id);
}