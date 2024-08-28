package dkcorp.user_service.repository;

import dkcorp.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "INSERT INTO subscription (follower_user_id, followee_user_id) VALUES (:followerId, :followeeId)", nativeQuery = true)
    void follow(Long followerId, Long followeeId);

    @Modifying
    @Query(value = "DELETE FROM subscription WHERE follower_user_id = :followerId AND followee_user_id = :followeeId", nativeQuery = true)
    void unfollow(Long followerId, Long followeeId);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM subscription WHERE follower_user_id = :followerId AND followee_user_id = :followeeId", nativeQuery = true)
    boolean isFollowing(Long followerId, Long followeeId);

    @Query(value = "SELECT u.* FROM users u " +
            "INNER JOIN subscription s ON u.id = s.follower_user_id " +
            "WHERE s.followee_user_id = :followeeId", nativeQuery = true)
    List<User> findFollowersByFolloweeId(Long followeeId);

    @Query(value = "SELECT COUNT(*) FROM subscription WHERE followee_user_id = :followeeId", nativeQuery = true)
    Long countFollowersByFolloweeId(Long followeeId);

    @Query(value = "SELECT u.* FROM users u " +
            "INNER JOIN subscription f ON u.id = f.followee_user_id " +
            "WHERE f.follower_user_id = :followerId", nativeQuery = true)
    List<User> findFollowsByFollowerId(Long followerId);

    @Query(value = "SELECT COUNT(*) FROM subscription WHERE follower_user_id = :followerId", nativeQuery = true)
    Long countFollowsByFollowerId(Long followerId);
}
