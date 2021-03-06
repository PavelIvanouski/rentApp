package by.academy.rentApp.controller;

import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.model.entity.User;
import by.academy.rentApp.service.UserService;
import by.academy.rentApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/login"})
    public String login(Model model) {
        return "login";
    }


    @GetMapping(value = "/registration")
    public String registration(Model model) {
//        model.addAttribute("user", new User());
        model.addAttribute("user", new UserFormDto());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String createNewUser(@Validated @ModelAttribute("user") UserFormDto user
            , BindingResult bindingResult, Model model) {
        UserFormDto userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.UserFormDto",
                            "There is already a user registered with the user name provided");
            return "registration";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult
                    .rejectValue("passwordConfirm", "error.UserFormDto",
                            "Password mismatch");
            return "registration";
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user, true);
            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("user", new UserFormDto());
            return "registration";
        }
    }

}
