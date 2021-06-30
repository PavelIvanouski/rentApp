package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.RoleDto;
import by.academy.rentApp.mapper.BrandMapper;
import by.academy.rentApp.mapper.RoleMapper;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Role;
import by.academy.rentApp.model.repository.BrandRepository;
import by.academy.rentApp.model.repository.RoleRepository;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach(role -> {
            roleDtos.add(RoleMapper.INSTANCE.roleToRoleDto(role));
        });
        return roleDtos;
    }

    @Override
    @Transactional
    public RoleDto saveRole(RoleDto roleDto) {
        Role savedRole = roleRepository.save(RoleMapper.INSTANCE.roleDtoToRole(roleDto));
        return RoleMapper.INSTANCE.roleToRoleDto(savedRole);
    }

    @Override
    public RoleDto findRoleById(Integer id) {
        Role role = roleRepository.findRoleById(id);
        return RoleMapper.INSTANCE.roleToRoleDto(role);
    }

    @Override
    public void deleteRole(RoleDto roleDto) {
        roleRepository.delete(RoleMapper.INSTANCE.roleDtoToRole(roleDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return roleRepository.existsById(id);
    }

}
