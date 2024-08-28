package dkcorp.user_service.controller;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.service.subscription.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/subscription")
@Tag(name = "Subscription api", description = "Operations related to subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    // followerId: это ID пользователя, который инициирует подписку (т.е. тот, кто подписывается)
    // followeeId: это ID пользователя, на которого подписываются (т.е. тот, на кого подписываются)

    @PostMapping("/{followerId}/follow/{followeeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Follow a user", description = "Allows a user to follow another user by their IDs")
    public void followUser(@PathVariable @NotNull Long followerId, @PathVariable @NotNull Long followeeId) {
        subscriptionService.followUser(followerId, followeeId);
    }

    @DeleteMapping("/{followerId}/unfollow/{followeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Unfollow a user", description = "Allows a user to unfollow another user by their IDs")
    public void unfollowUser(@PathVariable @NotNull Long followerId, @PathVariable @NotNull Long followeeId) {
        subscriptionService.unfollowUser(followerId, followeeId);
    }

    // Получить подписчиков
    @GetMapping("/{followeeId}/followers")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get followers", description = "Retrieves a list of users who are following the specified user")
    public List<UserDto> getFollowers(@PathVariable @NotNull Long followeeId) {
        return subscriptionService.getFollowers(followeeId);
    }

    @GetMapping("/{followeeId}/followers/count")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get followers count", description = "Retrieves the count of followers for a specific user")
    public Long getFollowersAmount(@PathVariable @NotNull Long followeeId) {
        return subscriptionService.getFollowersAmount(followeeId);
    }

    // Получить подписки
    @GetMapping("/{followerId}/follows")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get followees", description = "Retrieves a list of users that the specified user is following")
    public List<UserDto> getFollows(@PathVariable @NotNull Long followerId) {
        return subscriptionService.getFollows(followerId);
    }

    @GetMapping("/{followerId}/follows/count")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get followees count", description = "Retrieves the count of users that a specific user is following")
    public Long getFollowsAmount(@PathVariable @NotNull Long followerId) {
        return subscriptionService.getFollowsAmount(followerId);
    }
}
