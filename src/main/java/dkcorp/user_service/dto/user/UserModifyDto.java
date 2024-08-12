package dkcorp.user_service.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModifyDto {

    @NotBlank(message = "Username is mandatory. Example: john_doe")
    @Size(min = 3, max = 30, message = "Username should be between 3 and 30 characters. Example: john_doe")
    private String username;

    @NotBlank(message = "Email is mandatory. Example: user@example.com")
    @Email(message = "Email should be valid. Example: user@example.com")
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is not valid. Example: +1 (234) 567-8901")
    private String phone;

    @NotBlank(message = "Password is mandatory. Example: Pa$$w0rd")
    @Size(min = 6, message = "Password should have at least 6 characters. Example: Pa$$w0rd")
    private String password;

    @Size(max = 50, message = "First Name should not exceed 50 characters. Example: John")
    private String firstName;

    @Size(max = 50, message = "Last Name should not exceed 50 characters. Example: Doe")
    private String lastName;

    @Size(max = 250, message = "About Me should not exceed 250 characters. Example: I am a software developer with a passion for coding.")
    private String aboutMe;

    @NotBlank(message = "Date of Birth is mandatory. Example: 01-01-1990")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Date of Birth must be in the format dd-MM-yyyy. Example: 01-01-1990")
    private LocalDate dateOfBirth;
}
