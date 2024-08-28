package dkcorp.user_service.validator.subscription;

import dkcorp.user_service.exception.DataValidationException;
import dkcorp.user_service.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionValidatorTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionValidatorImpl subscriptionValidator;

    @Test
    void validateFollow_shouldThrowException_whenFollowerIsSameAsFollowee() {
        Long userId = 1L;

        assertThrows(DataValidationException.class, () ->
                        subscriptionValidator.validateFollow(userId, userId),
                "User cannot follow himself"
        );
    }

    @Test
    void validateFollow_shouldThrowException_whenAlreadyFollowing() {
        Long followerId = 1L;
        Long followeeId = 2L;

        when(subscriptionRepository.isFollowing(followerId, followeeId)).thenReturn(true);

        assertThrows(DataValidationException.class, () ->
                        subscriptionValidator.validateFollow(followerId, followeeId),
                String.format("User with id=%d is already following user with id=%d", followerId, followeeId)
        );

        verify(subscriptionRepository, times(1)).isFollowing(followerId, followeeId);
    }

    @Test
    void validateFollow_shouldSucceed_whenNotFollowingAndDifferentUsers() {
        Long followerId = 1L;
        Long followeeId = 2L;

        when(subscriptionRepository.isFollowing(followerId, followeeId)).thenReturn(false);

        assertDoesNotThrow(() ->
                subscriptionValidator.validateFollow(followerId, followeeId)
        );

        verify(subscriptionRepository, times(1)).isFollowing(followerId, followeeId);
    }

    @Test
    void validateUnfollow_shouldThrowException_whenFollowerIsSameAsFollowee() {
        Long userId = 1L;

        assertThrows(DataValidationException.class, () ->
                        subscriptionValidator.validateUnfollow(userId, userId),
                "User cannot unfollow himself"
        );
    }

    @Test
    void validateUnfollow_shouldThrowException_whenNotFollowing() {
        Long followerId = 1L;
        Long followeeId = 2L;

        when(subscriptionRepository.isFollowing(followerId, followeeId)).thenReturn(false);

        assertThrows(DataValidationException.class, () ->
                        subscriptionValidator.validateUnfollow(followerId, followeeId),
                String.format("User with id=%d cannot unfollow user with id=%d because he is not following", followerId, followeeId)
        );

        verify(subscriptionRepository, times(1)).isFollowing(followerId, followeeId);
    }

    @Test
    void validateUnfollow_shouldSucceed_whenFollowingAndDifferentUsers() {
        Long followerId = 1L;
        Long followeeId = 2L;

        when(subscriptionRepository.isFollowing(followerId, followeeId)).thenReturn(true);

        assertDoesNotThrow(() ->
                subscriptionValidator.validateUnfollow(followerId, followeeId)
        );

        verify(subscriptionRepository, times(1)).isFollowing(followerId, followeeId);
    }
}
