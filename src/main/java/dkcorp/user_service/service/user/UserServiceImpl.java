package dkcorp.user_service.service.user;

import dkcorp.user_service.dto.UserDto;
import dkcorp.user_service.entity.Country;
import dkcorp.user_service.entity.User;
import dkcorp.user_service.exception.EntityNotFoundException;
import dkcorp.user_service.mapper.UserMapper;
import dkcorp.user_service.repository.UserRepository;
import dkcorp.user_service.service.country.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CountryService countryService;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        Country country = countryService.findById(userDto.getCountryId());
        User newUser = userMapper.toEntity(userDto);
        newUser.setCountry(country);
        newUser.setActive(true);
        return userMapper.toDto(newUser);
    }

    @Override
    public UserDto findById(long userId) {
        return userMapper.toDto(findUserById(userId));
    }

    @Override
    public boolean existsById(long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public UserDto deactivateUser(long userId) {
        User user = findUserById(userId);
        user.setActive(false);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User updatedUser = userMapper.toEntity(userDto);
        updatedUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(updatedUser);
        return userMapper.toDto(updatedUser);
    }

    private User findUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%d not found", userId)));
    }
}
