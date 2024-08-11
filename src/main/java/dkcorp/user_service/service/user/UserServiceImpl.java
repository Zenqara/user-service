package dkcorp.user_service.service.user;

import dkcorp.user_service.dto.UserDto;
import dkcorp.user_service.entity.User;
import dkcorp.user_service.exception.EntityNotFoundException;
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
        return userMapper.toDto(findUserById(userId));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User newUser = userMapper.toEntity(userDto);
        newUser.setActive(true);
        return userMapper.toDto(userRepository.save(newUser));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User existingUser = findUserById(userId);
        userMapper.updateUserFromDto(userDto, existingUser);
        return userMapper.toDto(userRepository.save(existingUser));
    }

    @Override
    public UserDto deactivateUser(Long userId) {
        User user = findUserById(userId);
        user.setActive(false);
        return userMapper.toDto(userRepository.save(user));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%d not found", userId)));
    }
}
