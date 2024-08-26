package dkcorp.user_service.controller;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.dto.user.UserModifyDto;
import dkcorp.user_service.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User-service API", description = "Operations related to Users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    public List<UserDto> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find user by ID", description = "Returns a single user by their ID")
    public UserDto findUserById(@PathVariable @NotNull Long userId) {
        return userService.findById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user", description = "Creates a new user and returns the created user")
    public UserDto createUser(@RequestBody @Valid UserModifyDto userModifyDto) {
        return userService.createUser(userModifyDto);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing user", description = "Updates an existing user by their ID")
    public UserDto updateUser(@PathVariable @NotNull Long userId, @RequestBody @Valid UserModifyDto userModifyDto) {
        return userService.updateUser(userId, userModifyDto);
    }

    @PutMapping("/{userId}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deactivate a user", description = "Deactivates a user by their ID")
    public void deactivateUser(@PathVariable @NotNull Long userId) {
        userService.deactivateUser(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    public void deleteUser(@PathVariable @NotNull Long userId) {
        userService.deleteUser(userId);
    }
}
