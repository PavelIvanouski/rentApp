package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.RoleDto;
import by.academy.rentApp.mapper.RoleMapper;
import by.academy.rentApp.model.entity.Role;
import by.academy.rentApp.model.repository.RoleRepository;
import by.academy.rentApp.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleMapper roleMapper, RoleRepository roleRepository) {
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach(role -> {
            roleDtos.add(roleMapper.roleToRoleDto(role));
        });
        return roleDtos;
    }

    @Override
    @Transactional
    public RoleDto saveRole(RoleDto roleDto) {
        Role savedRole = roleRepository.save(roleMapper.roleDtoToRole(roleDto));
        return roleMapper.roleToRoleDto(savedRole);
    }

    @Override
    public RoleDto findRoleById(Integer id) {
        Role role = roleRepository.findRoleById(id);
        return roleMapper.roleToRoleDto(role);
    }

    @Override
    public RoleDto findRoleByName(String name) {
        Role role = roleRepository.findRoleByRole(name);
        return roleMapper.roleToRoleDto(role);
    }

    @Override
    public void deleteRole(RoleDto roleDto) {
        roleRepository.delete(roleMapper.roleDtoToRole(roleDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return roleRepository.existsById(id);
    }

}
