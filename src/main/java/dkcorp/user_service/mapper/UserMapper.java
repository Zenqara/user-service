package dkcorp.user_service.mapper;

import dkcorp.user_service.dto.UserDto;
import dkcorp.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);

    void updateUserFromDto(UserDto userDto, @MappingTarget User user);
}
