package dkcorp.user_service.validator.subscription;

import dkcorp.user_service.exception.DataValidationException;
import dkcorp.user_service.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionValidatorImpl implements SubscriptionValidator {
    private static final String ERROR_SELF_FOLLOW = "User cannot follow himself";
    private static final String ERROR_SELF_UNFOLLOW = "User cannot unfollow himself";
    private static final String ERROR_ALREADY_FOLLOWING = "User with id=%d is already following user with id=%d";
    private static final String ERROR_NOT_FOLLOWING = "User with id=%d cannot unfollow user with id=%d because he is not following";

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void validateFollow(Long followerId, Long followeeId) {
        checkSelfFollow(followerId, followeeId);
        checkAlreadyFollowing(followerId, followeeId);
    }

    @Override
    public void validateUnfollow(Long followerId, Long followeeId) {
        checkSelfUnfollow(followerId, followeeId);
        checkNotFollowing(followerId, followeeId);
    }

    private void checkSelfFollow(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new DataValidationException(ERROR_SELF_FOLLOW);
        }
    }

    private void checkSelfUnfollow(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new DataValidationException(ERROR_SELF_UNFOLLOW);
        }
    }

    private void checkAlreadyFollowing(Long followerId, Long followeeId) {
        if (isFollowing(followerId, followeeId)) {
            throw new DataValidationException(String.format(ERROR_ALREADY_FOLLOWING, followerId, followeeId));
        }
    }

    private void checkNotFollowing(Long followerId, Long followeeId) {
        if (!isFollowing(followerId, followeeId)) {
            throw new DataValidationException(String.format(ERROR_NOT_FOLLOWING, followerId, followeeId));
        }
    }

    private boolean isFollowing(Long followerId, Long followeeId) {
        return subscriptionRepository.isFollowing(followerId, followeeId);
    }
}
