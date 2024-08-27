package dkcorp.user_service.repository;

import dkcorp.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "INSERT INTO subscription (follower_user_id, followe_user_id) VALUES (:followerId, :followeeId)", nativeQuery = true)
    void subscribe(Long followerId, Long followeeId);
}
