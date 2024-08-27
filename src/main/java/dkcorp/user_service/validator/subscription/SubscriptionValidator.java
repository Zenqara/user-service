package dkcorp.user_service.validator.subscription;

public interface SubscriptionValidator {
    void validateSubscription(Long followerId, Long followeeId);
}
