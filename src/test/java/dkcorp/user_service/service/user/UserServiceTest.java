package dkcorp.user_service.service.user;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.dto.user.UserModifyDto;
import dkcorp.user_service.entity.User;
import dkcorp.user_service.exception.NotFoundException;
import dkcorp.user_service.mapper.UserMapper;
import dkcorp.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    void findAll_shouldReturnListOfUserDtos() {
        List<User> users = List.of(
                User.builder().id(1L).username("john_doe").email("john@example.com").build(),
                User.builder().id(2L).username("jane_doe").email("jane@example.com").build()
        );

        List<UserDto> userDtos = List.of(
                UserDto.builder().id(1L).username("john_doe").email("john@example.com").build(),
                UserDto.builder().id(2L).username("jane_doe").email("jane@example.com").build()
        );

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDtoList(users)).thenReturn(userDtos);

        List<UserDto> result = userService.findAll();

        assertEquals(userDtos, result);
    }

    @Test
    void findById_shouldReturnUserDto() {
        Long userId = 1L;
        User user = User.builder().id(userId).username("john_doe").email("john@example.com").build();
        UserDto userDto = UserDto.builder().id(userId).username("john_doe").email("john@example.com").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.entityToDto(user)).thenReturn(userDto);

        UserDto result = userService.findById(userId);

        assertEquals(userDto, result);
    }

    @Test
    void createUser_shouldReturnCreatedUserDto() {
        UserModifyDto userModifyDto = UserModifyDto.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("Pa$$w0rd")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        User newUser = User.builder()
                .id(1L)
                .username(userModifyDto.getUsername())
                .email(userModifyDto.getEmail())
                .active(true)
                .build();

        UserDto createdUserDto = UserDto.builder()
                .id(1L)
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .build();

        when(userMapper.modifyDtoToEntity(userModifyDto)).thenReturn(newUser);
        when(userRepository.save(newUser)).thenReturn(newUser);
        when(userMapper.entityToDto(newUser)).thenReturn(createdUserDto);

        UserDto result = userService.createUser(userModifyDto);

        assertEquals(createdUserDto, result);
    }

    @Test
    void updateUser_shouldReturnUpdatedUserDto() {
        Long userId = 1L;
        UserModifyDto userModifyDto = UserModifyDto.builder()
                .username("john_doe_updated")
                .email("john_updated@example.com")
                .password("NewPa$$w0rd")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        User existingUser = User.builder()
                .id(userId)
                .username("john_doe")
                .email("john@example.com")
                .build();

        UserDto updatedUserDto = UserDto.builder()
                .id(userId)
                .username(userModifyDto.getUsername())
                .email(userModifyDto.getEmail())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userMapper.updateUserFromModifyDto(userModifyDto, existingUser);

        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(userMapper.entityToDto(existingUser)).thenReturn(updatedUserDto);

        UserDto result = userService.updateUser(userId, userModifyDto);

        assertEquals(updatedUserDto, result);
    }

    @Test
    void deactivateUser_shouldDeactivateUser() {
        Long userId = 1L;
        User user = User.builder().id(userId).username("john_doe").email("john@example.com").active(true).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deactivateUser(userId);

        assertFalse(user.isActive());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        Long userId = 1L;
        User user = User.builder().id(userId).username("john_doe").email("john@example.com").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deactivateUser_shouldThrowExceptionWhenUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deactivateUser(userId));

        verify(userRepository, times(0)).save(any());
    }

    @Test
    void deleteUser_shouldThrowExceptionWhenUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUser(userId));

        verify(userRepository, times(0)).delete(any());
    }
}
