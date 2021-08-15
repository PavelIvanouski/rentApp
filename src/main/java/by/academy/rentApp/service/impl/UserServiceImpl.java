package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.mapper.UserMapper;
import by.academy.rentApp.model.entity.Role;
import by.academy.rentApp.model.entity.User;
import by.academy.rentApp.model.repository.RoleRepository;
import by.academy.rentApp.model.repository.UserRepository;
import by.academy.rentApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

//    public User findUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

    public UserDto findUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return userMapper.userToUserDto(user);
//        return userRepository.findByUserName(userName);
    }

    public User saveUser(User user) {
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        if (user.getId() == null) {
//            user.setCreatingDate(sqlTimestamp);
            user.setCreatingDate(OffsetDateTime.now());
        } else {
//            user.setUpdatingDate(sqlTimestamp);
            user.setUpdatingDate(OffsetDateTime.now());
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
//        Role userRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

}
