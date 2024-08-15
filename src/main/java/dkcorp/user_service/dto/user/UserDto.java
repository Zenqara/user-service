package dkcorp.user_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String firstName;
    private String lastName;
    private String aboutMe;
    private LocalDate dateOfBirth;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
