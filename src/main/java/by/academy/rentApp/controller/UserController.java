package by.academy.rentApp.controller;


import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.dto.TypeDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.service.UserService;
import by.academy.rentApp.service.impl.UserServiceImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model, @Param("keyword") String keyword) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("users", userService.getAll(keyword));
        return "user/users-admin";
    }

    @GetMapping("/admin/users/{id}")
    public String getAdminUserForm(@PathVariable Integer id, Model model) {
        if (!userService.existsById(id)) {
            return "redirect:/admin/users";
        }
        UserFormDto userFormDto = userService.findUserById(id);
        model.addAttribute("user", userFormDto);
        return "user/user-admin";
    }

    @PostMapping("/admin/users/{id}")
    public String updateUserAdmin(@Validated @ModelAttribute("user") UserFormDto userFormDto
            , BindingResult bindingResult
            , Model model) {
        UserFormDto userBeforeUpd = userService.findUserById(userFormDto.getId());
        userFormDto.setCreatingDate(userBeforeUpd.getCreatingDate());
        userFormDto.setUpdatingDate(userBeforeUpd.getUpdatingDate());

        if (bindingResult.hasErrors()) {
            return "user/user-admin";
        }

        userService.saveUser(userFormDto,false);
        model.addAttribute("user", userFormDto);
        model.addAttribute("successMessage","user saved");
        return "user/user-admin";
    }

    @GetMapping("/user/info")
    public String getUserInfoForm(@AuthenticationPrincipal User userSec, Model model) {
        model.addAttribute("user", userService.findUserByUserName(userSec.getUsername()));
        return "user/user-info";
    }

    @PostMapping("/user/info")
    public String updateUserInfo(@Validated @ModelAttribute("user") UserFormDto userFormDto
            , BindingResult bindingResult
            , Model model) {

        UserFormDto userBeforeUpd = userService.findUserById(userFormDto.getId());
        userFormDto.setCreatingDate(userBeforeUpd.getCreatingDate());
        userFormDto.setUpdatingDate(userBeforeUpd.getUpdatingDate());
        if (bindingResult.hasErrors()) {
            return "user/user-info";
        }
        userService.saveUser(userFormDto,false);
        model.addAttribute("successMessage","user saved");
        return "user/user-info";
    }
}
