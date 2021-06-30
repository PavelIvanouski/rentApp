package by.academy.rentApp.service;

import by.academy.rentApp.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getAll();

    RoleDto saveRole(RoleDto roleDto);

    RoleDto findRoleById(Integer id);

    void deleteRole(RoleDto roleDto);

    boolean existsById(Integer id);

}
