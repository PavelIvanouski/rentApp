package by.academy.rentApp.controller;

import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.model.entity.User;
import by.academy.rentApp.service.UserService;
import by.academy.rentApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/login"})
    public String login(Model model) {
        return "login";
    }


    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        UserDto userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
            return "registration";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult
                    .rejectValue("passwordConfirm", "error.user",
                            "Password mismatch");
            return "registration";
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
//            userService.saveUser(user);
            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("user", new User());
            return "registration";
        }
    }

}
