package dkcorp.user_service.service.user;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.dto.user.UserModifyDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long userId);

    UserDto createUser(UserModifyDto userModifyDto);

    UserDto updateUser(Long userId, UserModifyDto userModifyDto);

    void deactivateUser(Long userId);

    void deleteUser(Long userId);
}
