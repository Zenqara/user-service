package dkcorp.user_service.service.user;

import dkcorp.user_service.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long userId);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);

    UserDto deactivateUser(Long userId);
}
