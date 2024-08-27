package dkcorp.user_service.service.user;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.dto.user.UserModifyDto;
import dkcorp.user_service.entity.User;
import dkcorp.user_service.exception.NotFoundException;
import dkcorp.user_service.mapper.UserMapper;
import dkcorp.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserDto findById(Long userId) {
        return userMapper.entityToDto(findUserById(userId));
    }

    @Override
    public UserDto createUser(UserModifyDto userModifyDto) {
        User newUser = userMapper.modifyDtoToEntity(userModifyDto);
        newUser.setActive(true);
        return userMapper.entityToDto(userRepository.save(newUser));
    }

    @Override
    public UserDto updateUser(Long userId, UserModifyDto userModifyDto) {
        User existingUser = findUserById(userId);
        userMapper.updateUserFromModifyDto(userModifyDto, existingUser);
        return userMapper.entityToDto(userRepository.save(existingUser));
    }

    @Override
    public void deactivateUser(Long userId) {
        User user = findUserById(userId);
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.delete(findUserById(userId));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id=%d not found", userId)));
    }
}
