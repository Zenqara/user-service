package dkcorp.user_service.service.subscription;

import dkcorp.user_service.dto.user.UserDto;

import java.util.List;

public interface SubscriptionService {
    void followUser(Long followerId, Long followeeId);

    void unfollowUser(Long followerId, Long followeeId);

    List<UserDto> getFollowers(Long followeeId);

    Long getFollowersAmount(Long followeeId);

    List<UserDto> getFollows(Long followerId);

    Long getFollowsAmount(Long followerId);
}
