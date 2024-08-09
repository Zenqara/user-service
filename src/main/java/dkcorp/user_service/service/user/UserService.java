package dkcorp.user_service.service.user;

import dkcorp.user_service.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto findById(Long userId);

    boolean existsById(Long userId);

    UserDto deactivateUser(Long userId);

    List<UserDto> findAll();

    UserDto updateUser(Long userId, UserDto userDto);
}
