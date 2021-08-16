package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.RoleDto;
import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.mapper.RoleMapper;
import by.academy.rentApp.mapper.UserMapper;
import by.academy.rentApp.model.entity.Role;
import by.academy.rentApp.model.entity.User;
import by.academy.rentApp.model.repository.RoleRepository;
import by.academy.rentApp.model.repository.UserRepository;
import by.academy.rentApp.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;


    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

//    public User findUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

    @Override
    public List<UserDto> getAll(String keyword) {
        if (keyword != null) {
            List<User> users = userRepository.search(keyword);
            List<UserDto> userDtos = new ArrayList<>();
            users.forEach(user -> {
                userDtos.add(userMapper.userToUserDto(user));
            });
            return userDtos;
        }
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            userDtos.add(userMapper.userToUserDto(user));
        });
        return userDtos;
    }

    public UserFormDto findUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return userMapper.userToUserFormDto(user);
//        return userRepository.findByUserName(userName);
    }

    @Override
    public UserFormDto findUserById(Integer id) {
        User user = userRepository.findUserById(id);
        return userMapper.userToUserFormDto(user);
    }

    @Transactional
    public UserFormDto saveUser(UserFormDto userFormDto, Boolean newPassword) {
        if (userFormDto.getId() == null) {
            userFormDto.setActive(true);
            userFormDto.setCreatingDate(OffsetDateTime.now());
            if (userFormDto.getRoles() == null) {
                RoleDto roleDto = roleMapper.roleToRoleDto(roleRepository.findByRole("USER"));
                userFormDto.setRoles(new HashSet<RoleDto>(Arrays.asList(roleDto)));
            }
        } else {
            userFormDto.setUpdatingDate(OffsetDateTime.now());
            userFormDto.setActive(userFormDto.getActive());
        }
        if (newPassword) {
            userFormDto.setPassword(bCryptPasswordEncoder.encode(userFormDto.getPassword()));
        }

//        Role userRole = roleRepository.findByRole("ADMIN");
//        Role userRole = roleRepository.findByRole("USER");
//        userFormDto.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        User savedUser = userRepository.save(userMapper.userFormDtoToUser(userFormDto));
        return userMapper.userToUserFormDto(savedUser);
    }

    @Override
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

}
