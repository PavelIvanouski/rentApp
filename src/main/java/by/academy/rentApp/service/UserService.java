package by.academy.rentApp.service;

import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.model.entity.User;

import java.util.List;

public interface UserService {

    UserDto findUserByUserName(String userName);

}
