package dkcorp.user_service.repository;

import dkcorp.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "INSERT INTO subscription (follower_user_id, followee_user_id) VALUES (:followerId, :followeeId)", nativeQuery = true)
    void follow(Long followerId, Long followeeId);  // Было: subscribe

    @Modifying
    @Query(value = "DELETE FROM subscription WHERE follower_user_id = :followerId AND followee_user_id = :followeeId", nativeQuery = true)
    void unfollow(Long followerId, Long followeeId);  // Было: unsubscribe

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM subscription WHERE follower_user_id = :followerId AND followee_user_id = :followeeId", nativeQuery = true)
    boolean isFollowing(Long followerId, Long followeeId);  // Было: isSubscribing
}
