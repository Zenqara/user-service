package dkcorp.user_service.mapper;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.dto.user.UserModifyDto;
import dkcorp.user_service.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void mapEntityToDtoTest() {
        User user = User.builder()
                .id(1L)
                .username("IvanKiller2014")
                .email("ivanivanov@gmail.com")
                .phone("123456789440")
                .password("securepassword123")
                .firstName("Ivan")
                .lastName("Doe")
                .aboutMe("I like pizza")
                .dateOfBirth(LocalDate.parse("2014-01-15"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .subscribers(new HashSet<>())
                .subscribedUsers(new HashSet<>())
                .build();

        UserDto userDto = userMapper.entityToDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPhone(), userDto.getPhone());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getAboutMe(), userDto.getAboutMe());
        assertEquals(user.getDateOfBirth(), userDto.getDateOfBirth());
        assertTrue(userDto.isActive());
        assertEquals(user.getCreatedAt(), userDto.getCreatedAt());
        assertEquals(user.getUpdatedAt(), userDto.getUpdatedAt());
    }

    @Test
    void mapNullUserToDtoTest() {
        UserDto userDto = userMapper.entityToDto(null);
        assertNull(userDto);
    }

    @Test
    void mapModifyDtoToEntityTest() {
        UserModifyDto userModifyDto = UserModifyDto.builder()
                .username("IvanKiller2014")
                .email("ivanivanov@gmail.com")
                .phone("123456789440")
                .password("securepassword123")
                .firstName("Ivan")
                .lastName("Doe")
                .aboutMe("I like pizza")
                .dateOfBirth(LocalDate.parse("2014-01-15"))
                .build();

        User user = userMapper.modifyDtoToEntity(userModifyDto);

        assertNotNull(user);
        assertEquals(userModifyDto.getUsername(), user.getUsername());
        assertEquals(userModifyDto.getEmail(), user.getEmail());
        assertEquals(userModifyDto.getPhone(), user.getPhone());
        assertEquals(userModifyDto.getFirstName(), user.getFirstName());
        assertEquals(userModifyDto.getLastName(), user.getLastName());
        assertEquals(userModifyDto.getAboutMe(), user.getAboutMe());
        assertEquals(userModifyDto.getDateOfBirth(), user.getDateOfBirth());
    }

    @Test
    void mapNullModifyDtoToUserTest() {
        User user = userMapper.modifyDtoToEntity(null);
        assertNull(user);
    }

    @Test
    void mapUserListToDtoListTest() {
        User user1 = User.builder()
                .id(1L)
                .username("IvanKiller2014")
                .email("ivanivanov@gmail.com")
                .phone("1234567890")
                .password("securepassword123")
                .firstName("Ivan")
                .lastName("Doe")
                .aboutMe("I like pizza")
                .dateOfBirth(LocalDate.parse("2014-01-15"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .subscribers(new HashSet<>())
                .subscribedUsers(new HashSet<>())
                .build();

        User user2 = User.builder()
                .id(2L)
                .username("JaneDoe2022")
                .email("janedoe@gmail.com")
                .phone("0987654321")
                .password("anotherpassword321")
                .firstName("Jane")
                .lastName("Doe")
                .aboutMe("I love hiking")
                .dateOfBirth(LocalDate.parse("1990-05-22"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .subscribers(new HashSet<>())
                .subscribedUsers(new HashSet<>())
                .build();

        User user3 = User.builder()
                .id(3L)
                .username("JohnSmith")
                .email("johnsmith@gmail.com")
                .phone("1122334455")
                .password("mypassword123")
                .firstName("John")
                .lastName("Smith")
                .aboutMe("I enjoy programming")
                .dateOfBirth(LocalDate.parse("1985-10-10"))
                .active(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .subscribers(new HashSet<>())
                .subscribedUsers(new HashSet<>())
                .build();

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        List<UserDto> userDtoList = userMapper.toDtoList(users);

        assertNotNull(userDtoList);
        assertEquals(3, userDtoList.size());

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            UserDto userDto = userDtoList.get(i);

            assertEquals(user.getId(), userDto.getId());
            assertEquals(user.getUsername(), userDto.getUsername());
            assertEquals(user.getEmail(), userDto.getEmail());
            assertEquals(user.getPhone(), userDto.getPhone());
            assertEquals(user.getFirstName(), userDto.getFirstName());
            assertEquals(user.getLastName(), userDto.getLastName());
            assertEquals(user.getAboutMe(), userDto.getAboutMe());
            assertEquals(user.getDateOfBirth(), userDto.getDateOfBirth());
            assertEquals(user.isActive(), userDto.isActive());
            assertEquals(user.getCreatedAt(), userDto.getCreatedAt());
            assertEquals(user.getUpdatedAt(), userDto.getUpdatedAt());
        }
    }

    @Test
    void mapNullUserListToDtoListTest() {
        List<UserDto> userDtoList = userMapper.toDtoList(null);
        assertNull(userDtoList);
    }

    @Test
    void updateUserFromModifyDtoTest() {
        User user = User.builder()
                .id(1L)
                .username("TestUser")
                .email("testuser@gmail.com")
                .phone("1234567890")
                .password("password")
                .firstName("Test")
                .lastName("User")
                .aboutMe("About me")
                .dateOfBirth(LocalDate.now())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        String newUsername = "NewUsername";
        String newEmail = "newemail@gmail.com";
        String newPhone = "0987654321";
        String newPassword = "newpassword";
        String newFirstName = "NewFirstName";
        String newLastName = "NewLastName";
        String newAboutMe = "New about me";
        LocalDate newDateOfBirth = LocalDate.parse("2000-01-01");

        UserModifyDto userModifyDto = UserModifyDto.builder()
                .username(newUsername)
                .email(newEmail)
                .phone(newPhone)
                .password(newPassword)
                .firstName(newFirstName)
                .lastName(newLastName)
                .aboutMe(newAboutMe)
                .dateOfBirth(newDateOfBirth)
                .build();

        userMapper.updateUserFromModifyDto(userModifyDto, user);

        assertEquals(newUsername, user.getUsername());
        assertEquals(newEmail, user.getEmail());
        assertEquals(newPhone, user.getPhone());
        assertEquals(newPassword, user.getPassword());
        assertEquals(newFirstName, user.getFirstName());
        assertEquals(newLastName, user.getLastName());
        assertEquals(newAboutMe, user.getAboutMe());
        assertEquals(newDateOfBirth, user.getDateOfBirth());
    }

    @Test
    void updateUserFromNullModifyDtoTest() {
        User user = User.builder()
                .id(1L)
                .username("TestUser")
                .email("testuser@gmail.com")
                .phone("1234567890")
                .password("password")
                .firstName("Test")
                .lastName("User")
                .aboutMe("About me")
                .dateOfBirth(LocalDate.now())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        String initialUsername = user.getUsername();
        String initialEmail = user.getEmail();
        String initialPhone = user.getPhone();
        String initialPassword = user.getPassword();
        String initialFirstName = user.getFirstName();
        String initialLastName = user.getLastName();
        String initialAboutMe = user.getAboutMe();
        LocalDate initialDateOfBirth = user.getDateOfBirth();
        boolean initialActive = user.isActive();
        LocalDateTime initialCreatedAt = user.getCreatedAt();
        LocalDateTime initialUpdatedAt = user.getUpdatedAt();

        userMapper.updateUserFromModifyDto(null, user);

        assertEquals(initialUsername, user.getUsername());
        assertEquals(initialEmail, user.getEmail());
        assertEquals(initialPhone, user.getPhone());
        assertEquals(initialPassword, user.getPassword());
        assertEquals(initialFirstName, user.getFirstName());
        assertEquals(initialLastName, user.getLastName());
        assertEquals(initialAboutMe, user.getAboutMe());
        assertEquals(initialDateOfBirth, user.getDateOfBirth());
        assertEquals(initialActive, user.isActive());
        assertEquals(initialCreatedAt, user.getCreatedAt());
        assertEquals(initialUpdatedAt, user.getUpdatedAt());
    }
}
