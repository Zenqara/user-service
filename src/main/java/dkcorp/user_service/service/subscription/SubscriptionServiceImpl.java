package dkcorp.user_service.service.subscription;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.entity.User;
import dkcorp.user_service.mapper.UserMapper;
import dkcorp.user_service.repository.SubscriptionRepository;
import dkcorp.user_service.validator.subscription.SubscriptionValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionValidator subscriptionValidator;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void followUser(Long followerId, Long followeeId) {
        subscriptionValidator.validateFollow(followerId, followeeId);
        subscriptionRepository.follow(followerId, followeeId);
    }

    @Override
    @Transactional
    public void unfollowUser(Long followerId, Long followeeId) {
        subscriptionValidator.validateUnfollow(followerId, followeeId);
        subscriptionRepository.unfollow(followerId, followeeId);
    }

    @Override
    public List<UserDto> getFollowers(Long followeeId) {
        List<User> users = subscriptionRepository.findFollowersByFolloweeId(followeeId);
        return userMapper.toDtoList(users);
    }

    @Override
    public Long getFollowersAmount(Long followeeId) {
        return subscriptionRepository.countFollowersByFolloweeId(followeeId);
    }

    @Override
    public List<UserDto> getFollows(Long followerId) {
        List<User> users = subscriptionRepository.findFollowsByFollowerId(followerId);
        return userMapper.toDtoList(users);
    }

    @Override
    public Long getFollowsAmount(Long followerId) {
        return subscriptionRepository.countFollowsByFollowerId(followerId);
    }
}
