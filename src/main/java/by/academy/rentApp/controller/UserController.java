package by.academy.rentApp.controller;


import by.academy.rentApp.dto.RoleDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.model.entity.Role;
import by.academy.rentApp.service.RoleService;
import by.academy.rentApp.service.UserService;
import by.academy.rentApp.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
        userFormDto.setRoles(userBeforeUpd.getRoles());

        if (bindingResult.hasErrors()) {
            return "user/user-admin";
        }

        userService.saveUser(userFormDto, false);
        model.addAttribute("user", userFormDto);
        model.addAttribute("successMessage", "user saved");
        return "user/user-admin";
    }

    @GetMapping(value = "/admin/add")
    public String getAddAdminForm(Model model) {
        UserFormDto userFormDto = new UserFormDto();
        model.addAttribute("user", userFormDto);
        return "user/user-add";
    }

    @PostMapping(value = "/admin/add")
    public String AddAdmin(@Validated @ModelAttribute("user") UserFormDto userFormDto
            , BindingResult bindingResult
            , Model model) {
        UserFormDto userExists = userService.findUserByUserName(userFormDto.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.UserFormDto",
                            "There is already a user registered with the user name provided");
            return "user/user-add";
        }
        if (!userFormDto.getPassword().equals(userFormDto.getPasswordConfirm())) {
            bindingResult
                    .rejectValue("passwordConfirm", "error.UserFormDto",
                            "Password mismatch");
            return "user/user-add";
        }
        if (bindingResult.hasErrors()) {
            return "user/user-add";
        } else {
            RoleDto roleDto = roleService.findRoleByName("ADMIN");
            userFormDto.setRoles(new HashSet<RoleDto>(Arrays.asList(roleDto)));

            userService.saveUser(userFormDto, true);


            model.addAttribute("successMessage", "Admin added");
            model.addAttribute("user", new UserFormDto());
            return "user/user-add";
        }


    }


    @GetMapping("/user/info")
    public String getUserInfoForm(@AuthenticationPrincipal User userSec, Model model) {
        model.addAttribute("user", userService.findUserByUserName(userSec.getUsername()));
        return "user/user-info";
    }

    @PostMapping("/user/info")
    public String updateUserInfo(@Validated @ModelAttribute("user") UserFormDto userFormDto
            , @RequestParam(value = "changePassword", required = false) Boolean changePassword
            , @RequestParam(value = "newPassword", required = false) String newPassword
            , BindingResult bindingResult
            , Model model) {

        UserFormDto userBeforeUpd = userService.findUserById(userFormDto.getId());
        userFormDto.setCreatingDate(userBeforeUpd.getCreatingDate());
        userFormDto.setUpdatingDate(userBeforeUpd.getUpdatingDate());
        userFormDto.setRoles(userBeforeUpd.getRoles());
        if (bindingResult.hasErrors()) {
            return "user/user-info";
        }

        Boolean setNewPassword = false;
        if (changePassword != null) {
            if (changePassword && !"".equals(newPassword)) {
                userFormDto.setPassword(newPassword);
                setNewPassword = true;
            }
        }
        userService.saveUser(userFormDto, setNewPassword);
        model.addAttribute("successMessage", "user saved");
        return "user/user-info";
    }
}
