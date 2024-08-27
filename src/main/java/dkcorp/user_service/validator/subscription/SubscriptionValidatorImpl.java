package dkcorp.user_service.validator.subscription;

import dkcorp.user_service.exception.DataValidationException;
import dkcorp.user_service.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionValidatorImpl implements SubscriptionValidator {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void validateSubscription(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new DataValidationException("User cannot subscribe to himself");
        } else if (isFollowing(followerId, followeeId)) {
            throw new DataValidationException(String.format("User with id=%d is already following user with id=%d", followerId, followeeId));
        }
    }

    @Override
    public void validateUnsubscription(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new DataValidationException("User cannot unsubscribe from himself");
        } else if (!isFollowing(followerId, followeeId)) {
            throw new DataValidationException(String.format("User with id=%d cannot unsubscribe from user with id=%d because he is not subscribed", followerId, followeeId));
        }
    }

    private boolean isFollowing(Long followerId, Long followeeId) {
        return subscriptionRepository.isSubscribing(followerId, followeeId);
    }
}
