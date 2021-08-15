package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = CommonMapperConfig.class
        , uses = {RoleMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    UserFormDto userToUserFormDto(User user);

    User userFormDtoToUser(UserFormDto userFormDto);
}
