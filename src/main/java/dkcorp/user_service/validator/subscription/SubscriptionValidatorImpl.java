package dkcorp.user_service.validator.subscription;

import dkcorp.user_service.exception.DataValidationException;
import dkcorp.user_service.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionValidatorImpl implements SubscriptionValidator {
    private static final String ERROR_SELF_SUBSCRIPTION = "User cannot subscribe to himself";
    private static final String ERROR_SELF_UNSUBSCRIPTION = "User cannot unsubscribe from himself";
    private static final String ERROR_ALREADY_FOLLOWING = "User with id=%d is already following user with id=%d";
    private static final String ERROR_NOT_FOLLOWING = "User with id=%d cannot unsubscribe from user with id=%d because he is not subscribed";

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void validateSubscription(Long followerId, Long followeeId) {
        checkSelfSubscription(followerId, followeeId);
        checkAlreadyFollowing(followerId, followeeId);
    }

    @Override
    public void validateUnsubscription(Long followerId, Long followeeId) {
        checkSelfUnsubscription(followerId, followeeId);
        checkNotFollowing(followerId, followeeId);
    }

    private void checkSelfSubscription(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new DataValidationException(ERROR_SELF_SUBSCRIPTION);
        }
    }

    private void checkSelfUnsubscription(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new DataValidationException(ERROR_SELF_UNSUBSCRIPTION);
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
        return subscriptionRepository.isSubscribing(followerId, followeeId);
    }
}
