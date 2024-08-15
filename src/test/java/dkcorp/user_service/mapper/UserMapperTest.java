package dkcorp.user_service.mapper;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.dto.user.UserModifyDto;
import dkcorp.user_service.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserMapperTest {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void entityToDto_shouldConvertUserToUserDto() {
        User user = User.builder()
                .id(1L)
                .username("john_doe")
                .email("john@example.com")
                .build();

        UserDto userDto = userMapper.entityToDto(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void modifyDtoToEntity_shouldConvertUserModifyDtoToUser() {
        UserModifyDto userModifyDto = UserModifyDto.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("Pa$$w0rd")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        User user = userMapper.modifyDtoToEntity(userModifyDto);

        assertEquals(userModifyDto.getUsername(), user.getUsername());
        assertEquals(userModifyDto.getEmail(), user.getEmail());
    }

    @Test
    void toDtoList_shouldConvertListOfUsersToListOfUserDtos() {
        List<User> users = List.of(
                User.builder().id(1L).username("john_doe").email("john@example.com").build(),
                User.builder().id(2L).username("jane_doe").email("jane@example.com").build()
        );

        List<UserDto> userDtos = userMapper.toDtoList(users);

        assertEquals(users.size(), userDtos.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getId(), userDtos.get(i).getId());
            assertEquals(users.get(i).getUsername(), userDtos.get(i).getUsername());
            assertEquals(users.get(i).getEmail(), userDtos.get(i).getEmail());
        }
    }

    @Test
    void updateUserFromModifyDto_shouldUpdateExistingUser() {
        User existingUser = User.builder()
                .id(1L)
                .username("john_doe")
                .email("john@example.com")
                .build();

        UserModifyDto userModifyDto = UserModifyDto.builder()
                .username("john_doe_updated")
                .email("john_updated@example.com")
                .build();

        userMapper.updateUserFromModifyDto(userModifyDto, existingUser);

        assertEquals(userModifyDto.getUsername(), existingUser.getUsername());
        assertEquals(userModifyDto.getEmail(), existingUser.getEmail());
    }

    @Test
    void mapNullUserToDtoTest() {
        UserDto userDto = userMapper.entityToDto(null);
        assertNull(userDto);
    }

    @Test
    void mapNullModifyDtoToUserTest() {
        User user = userMapper.modifyDtoToEntity(null);
        assertNull(user);
    }


    @Test
    void mapNullUserListToDtoListTest() {
        List<UserDto> userDtoList = userMapper.toDtoList(null);
        assertNull(userDtoList);
    }
}
