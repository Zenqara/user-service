package dkcorp.user_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true, nullable = false, length = 64)
    private String username;

    @Column(name = "email", unique = true, nullable = false, length = 32)
    private String email;

    @Column(name = "phone", unique = true, length = 32)
    private String phone;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "first_name", length = 32)
    private String firstName;

    @Column(name = "last_name", length = 32)
    private String lastName;

    @Column(name = "about_me", length = 4096)
    private String aboutMe;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    // Пользователи, которые подписаны на данного пользователя
    @ManyToMany
    @JoinTable(name = "subscription",
            joinColumns = @JoinColumn(name = "subscribed_user_id"), // ID пользователя, на которого подписываются
            inverseJoinColumns = @JoinColumn(name = "subscriber_user_id")  // ID подписчика
    )
    private Set<User> subscribers = new HashSet<>();

    // Пользователи, на которых подписан данный пользователь
    @ManyToMany(mappedBy = "subscribers") // Указывает на то, что это обратная сторона связи
    private Set<User> subscribedUsers = new HashSet<>();
}
