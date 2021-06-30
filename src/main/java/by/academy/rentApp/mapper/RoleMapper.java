package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.RoleDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto roleToRoleDto(Role role);

    Role roleDtoToRole(RoleDto roleDto);
}
