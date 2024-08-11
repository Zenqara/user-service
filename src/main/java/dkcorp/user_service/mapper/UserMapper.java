package dkcorp.user_service.mapper;

import dkcorp.user_service.dto.user.UserDto;
import dkcorp.user_service.dto.user.UserModifyDto;
import dkcorp.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    UserDto entityToDto(User user);

    User modifyDtoToEntity(UserModifyDto userModifyDto);

    List<UserDto> toDtoList(List<User> users);

    void updateUserFromModifyDto(UserModifyDto userModifyDto, @MappingTarget User user);
}
