package dkcorp.user_service.service.user;

import dkcorp.user_service.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto findById(long userId);

    boolean existsById(long userId);

    UserDto deactivateUser(long userId);

    List<UserDto> findAll();

    UserDto update(UserDto userDto);
}
