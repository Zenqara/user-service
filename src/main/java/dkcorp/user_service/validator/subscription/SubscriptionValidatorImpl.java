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
        isAlreadyFollowing(followerId, followeeId);
        isSelfFollowing(followerId, followeeId);
    }

    private void isAlreadyFollowing(Long followerId, Long followeeId) {
        if (subscriptionRepository.isFollowing(followerId, followeeId)) {
            throw new DataValidationException(String.format("User with id=%d already following user with id=%d", followerId, followeeId));
        }
    }

    private void isSelfFollowing(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new DataValidationException("User cannot subscribe to himself");
        }
    }
}
