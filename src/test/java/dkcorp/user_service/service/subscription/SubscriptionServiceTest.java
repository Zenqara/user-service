package dkcorp.user_service.service.subscription;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.entity.User;
import dkcorp.user_service.mapper.UserMapper;
import dkcorp.user_service.repository.SubscriptionRepository;
import dkcorp.user_service.validator.subscription.SubscriptionValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionValidator subscriptionValidator;

    @Mock
    private UserMapper userMapper;

    @Test
    void followUser_shouldCallRepositoryAndValidator() {
        Long followerId = 1L;
        Long followeeId = 2L;

        subscriptionService.followUser(followerId, followeeId);

        verify(subscriptionValidator, times(1)).validateFollow(followerId, followeeId);
        verify(subscriptionRepository, times(1)).follow(followerId, followeeId);
    }

    @Test
    void unfollowUser_shouldCallRepositoryAndValidator() {
        Long followerId = 1L;
        Long followeeId = 2L;

        subscriptionService.unfollowUser(followerId, followeeId);

        verify(subscriptionValidator, times(1)).validateUnfollow(followerId, followeeId);
        verify(subscriptionRepository, times(1)).unfollow(followerId, followeeId);
    }

    @Test
    void getFollowers_shouldReturnListOfUserDtos() {
        Long followeeId = 1L;
        List<User> users = List.of(
                User.builder().id(1L).username("john_doe").build(),
                User.builder().id(2L).username("jane_doe").build()
        );
        List<UserDto> userDtos = List.of(
                UserDto.builder().id(1L).username("john_doe").build(),
                UserDto.builder().id(2L).username("jane_doe").build()
        );

        when(subscriptionRepository.findFollowersByFolloweeId(followeeId)).thenReturn(users);
        when(userMapper.toDtoList(users)).thenReturn(userDtos);

        List<UserDto> result = subscriptionService.getFollowers(followeeId);

        assertEquals(userDtos, result);
    }

    @Test
    void getFollowersAmount_shouldReturnCountOfFollowers() {
        Long followeeId = 1L;
        Long followerCount = 5L;

        when(subscriptionRepository.countFollowersByFolloweeId(followeeId)).thenReturn(followerCount);

        Long result = subscriptionService.getFollowersAmount(followeeId);

        assertEquals(followerCount, result);
    }

    @Test
    void getFollows_shouldReturnListOfUserDtos() {
        Long followerId = 1L;
        List<User> users = List.of(
                User.builder().id(1L).username("john_doe").build(),
                User.builder().id(2L).username("jane_doe").build()
        );
        List<UserDto> userDtos = List.of(
                UserDto.builder().id(1L).username("john_doe").build(),
                UserDto.builder().id(2L).username("jane_doe").build()
        );

        when(subscriptionRepository.findFollowsByFollowerId(followerId)).thenReturn(users);
        when(userMapper.toDtoList(users)).thenReturn(userDtos);

        List<UserDto> result = subscriptionService.getFollows(followerId);

        assertEquals(userDtos, result);
    }

    @Test
    void getFollowsAmount_shouldReturnCountOfFollows() {
        Long followerId = 1L;
        Long followCount = 7L;

        when(subscriptionRepository.countFollowsByFollowerId(followerId)).thenReturn(followCount);

        Long result = subscriptionService.getFollowsAmount(followerId);

        assertEquals(followCount, result);
    }

    @Test
    void followUser_shouldThrowExceptionWhenValidationFails() {
        Long followerId = 1L;
        Long followeeId = 2L;

        doThrow(new RuntimeException("Validation failed")).when(subscriptionValidator).validateFollow(followerId, followeeId);

        assertThrows(RuntimeException.class, () -> subscriptionService.followUser(followerId, followeeId));

        verify(subscriptionRepository, times(0)).follow(followerId, followeeId);
    }

    @Test
    void unfollowUser_shouldThrowExceptionWhenValidationFails() {
        Long followerId = 1L;
        Long followeeId = 2L;

        doThrow(new RuntimeException("Validation failed")).when(subscriptionValidator).validateUnfollow(followerId, followeeId);

        assertThrows(RuntimeException.class, () -> subscriptionService.unfollowUser(followerId, followeeId));

        verify(subscriptionRepository, times(0)).unfollow(followerId, followeeId);
    }
}
