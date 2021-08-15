package by.academy.rentApp.controller;


import by.academy.rentApp.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private final UserServiceImpl userService;


    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
//        model.addAttribute("users",userService.getAll());
        return "users";
    }
}
