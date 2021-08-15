package by.academy.rentApp.service;

import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.model.entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAll(String keyword);

    UserFormDto findUserByUserName(String userName);

    UserFormDto findUserById(Integer id);

    UserFormDto saveUser(UserFormDto userFormDto, Boolean newPassword);

    boolean existsById(Integer id);
}
