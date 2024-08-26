package dkcorp.user_service.controller;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.dto.user.UserModifyDto;
import dkcorp.user_service.exception.NotFoundException;
import dkcorp.user_service.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void findAllUsers_shouldReturnListOfUsers() {
        List<UserDto> users = List.of(
                UserDto.builder().id(1L).username("john_doe").email("john@example.com").build(),
                UserDto.builder().id(2L).username("jane_doe").email("jane@example.com").build()
        );

        when(userService.findAll()).thenReturn(users);

        List<UserDto> result = userController.findAllUsers();
        assertEquals(users, result);
    }

    @Test
    void findUserById_shouldReturnUser() {
        Long userId = 1L;
        UserDto user = UserDto.builder().id(userId).username("john_doe").email("john@example.com").build();

        when(userService.findById(userId)).thenReturn(user);

        UserDto result = userController.findUserById(userId);
        assertEquals(user, result);
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        UserModifyDto userModifyDto = UserModifyDto.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("Pa$$w0rd")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        UserDto createdUser = UserDto.builder()
                .id(1L)
                .username(userModifyDto.getUsername())
                .email(userModifyDto.getEmail())
                .dateOfBirth(userModifyDto.getDateOfBirth())
                .build();

        when(userService.createUser(any(UserModifyDto.class))).thenReturn(createdUser);

        UserDto result = userController.createUser(userModifyDto);
        assertEquals(createdUser, result);
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        Long userId = 1L;
        UserModifyDto userModifyDto = UserModifyDto.builder()
                .username("john_doe_updated")
                .email("john_updated@example.com")
                .password("NewPa$$w0rd")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        UserDto updatedUser = UserDto.builder()
                .id(userId)
                .username(userModifyDto.getUsername())
                .email(userModifyDto.getEmail())
                .dateOfBirth(userModifyDto.getDateOfBirth())
                .build();

        when(userService.updateUser(eq(userId), any(UserModifyDto.class))).thenReturn(updatedUser);

        UserDto result = userController.updateUser(userId, userModifyDto);
        assertEquals(updatedUser, result);
    }

    @Test
    void deactivateUser_shouldDeactivateUser() {
        Long userId = 1L;

        doNothing().when(userService).deactivateUser(userId);

        assertDoesNotThrow(() -> userController.deactivateUser(userId));

        verify(userService, times(1)).deactivateUser(userId);
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        assertDoesNotThrow(() -> userController.deleteUser(userId));

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void deactivateUser_shouldThrowExceptionWhenUserNotFound() {
        Long userId = 1L;

        doThrow(new NotFoundException("User not found")).when(userService).deactivateUser(userId);

        assertThrows(NotFoundException.class, () -> userController.deactivateUser(userId));

        verify(userService, times(1)).deactivateUser(userId);
    }

    @Test
    void deleteUser_shouldThrowExceptionWhenUserNotFound() {
        Long userId = 1L;

        doThrow(new NotFoundException("User not found")).when(userService).deleteUser(userId);

        assertThrows(NotFoundException.class, () -> userController.deleteUser(userId));

        verify(userService, times(1)).deleteUser(userId);
    }
}
