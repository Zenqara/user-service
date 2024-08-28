package dkcorp.user_service.controller;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.service.subscription.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTest {
    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionController subscriptionController;

    @Test
    void followUser_shouldCallService() {
        Long followerId = 1L;
        Long followeeId = 2L;

        assertDoesNotThrow(() -> subscriptionController.followUser(followerId, followeeId));

        verify(subscriptionService, times(1)).followUser(followerId, followeeId);
    }

    @Test
    void unfollowUser_shouldCallService() {
        Long followerId = 1L;
        Long followeeId = 2L;

        assertDoesNotThrow(() -> subscriptionController.unfollowUser(followerId, followeeId));

        verify(subscriptionService, times(1)).unfollowUser(followerId, followeeId);
    }

    @Test
    void getFollowers_shouldReturnListOfFollowers() {
        Long followeeId = 1L;
        List<UserDto> followers = List.of(UserDto.builder().id(2L).username("follower1").email("follower1@example.com").build(),
                UserDto.builder().id(3L).username("follower2").email("follower2@example.com").build());

        when(subscriptionService.getFollowers(followeeId)).thenReturn(followers);

        List<UserDto> result = subscriptionController.getFollowers(followeeId);

        assertEquals(followers, result);
        verify(subscriptionService, times(1)).getFollowers(followeeId);
    }

    @Test
    void getFollowersAmount_shouldReturnCount() {
        Long followeeId = 1L;
        Long count = 5L;

        when(subscriptionService.getFollowersAmount(followeeId)).thenReturn(count);

        Long result = subscriptionController.getFollowersAmount(followeeId);

        assertEquals(count, result);
        verify(subscriptionService, times(1)).getFollowersAmount(followeeId);
    }

    @Test
    void getFollows_shouldReturnListOfFollowees() {
        Long followerId = 1L;
        List<UserDto> followees = List.of(UserDto.builder().id(4L).username("followee1").email("followee1@example.com").build(),
                UserDto.builder().id(5L).username("followee2").email("followee2@example.com").build());

        when(subscriptionService.getFollows(followerId)).thenReturn(followees);

        List<UserDto> result = subscriptionController.getFollows(followerId);

        assertEquals(followees, result);
        verify(subscriptionService, times(1)).getFollows(followerId);
    }

    @Test
    void getFollowsAmount_shouldReturnCount() {
        Long followerId = 1L;
        Long count = 3L;

        when(subscriptionService.getFollowsAmount(followerId)).thenReturn(count);

        Long result = subscriptionController.getFollowsAmount(followerId);

        assertEquals(count, result);
        verify(subscriptionService, times(1)).getFollowsAmount(followerId);
    }
}
