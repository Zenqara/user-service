package dkcorp.user_service.controller;

import dkcorp.user_service.dto.UserDto;
import dkcorp.user_service.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<UserDto> findAllUsers() {
        return userService.findAll();
    }

    @Operation(summary = "Find user by ID", description = "Returns a single user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public UserDto findUserById(@Parameter(description = "ID of the user to be retrieved") @PathVariable Long userId) {
        return userService.findById(userId);
    }

    @Operation(summary = "Create a new user", description = "Creates a new user and returns the created user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PostMapping
    public UserDto createUser(@Parameter(description = "User data for the new user") @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @Operation(summary = "Update an existing user", description = "Updates an existing user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{userId}")
    public UserDto updateUser(
            @Parameter(description = "ID of the user to be updated") @PathVariable Long userId,
            @Parameter(description = "Updated user data") @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }

    @Operation(summary = "Deactivate a user", description = "Deactivates a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully deactivated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{userId}")
    public UserDto deactivateUser(@Parameter(description = "ID of the user to be deactivated") @PathVariable Long userId) {
        return userService.deactivateUser(userId);
    }
}
