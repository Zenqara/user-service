package dkcorp.user_service.validator.subscription;

public interface SubscriptionValidator {
    void validateFollow(Long followerId, Long followeeId);

    void validateUnfollow(Long followerId, Long followeeId);
}
