package by.academy.rentApp.controller;

import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.service.CarService;
import by.academy.rentApp.service.OrderService;
import by.academy.rentApp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/orders")
public class OrderController {

    private  final OrderService orderService;
    private final CarService carService;
    private final UserService userService;

    public OrderController(OrderService orderService, CarService carService, UserService userService) {
        this.orderService = orderService;
        this.carService = carService;
        this.userService = userService;
    }

    @PostMapping("/send")
    public String sendOrder(@RequestParam("carId") Integer id, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("car", carService.findCarById(id));
        model.addAttribute("user", userService.findUserByUserName(user.getUsername()));
        model.addAttribute("order", new OrderDto());
        return "order/order-add";

    }
}
