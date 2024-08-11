package dkcorp.user_service.controller;

import dkcorp.user_service.dto.UserDto;
import dkcorp.user_service.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User-service API", description = "Operations related to Users")
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    public List<UserDto> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Find user by ID", description = "Returns a single user by their ID")
    public UserDto findUserById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user and returns the created user")
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update an existing user", description = "Updates an existing user by their ID")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Deactivate a user", description = "Deactivates a user by their ID")
    public UserDto deactivateUser(@PathVariable Long userId) {
        return userService.deactivateUser(userId);
    }
}
