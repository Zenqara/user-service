package dkcorp.user_service.repository;

import dkcorp.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<User, Long> {
}
