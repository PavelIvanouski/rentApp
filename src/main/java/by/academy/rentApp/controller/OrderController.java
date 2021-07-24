package by.academy.rentApp.controller;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.service.CarService;
import by.academy.rentApp.service.OrderService;
import by.academy.rentApp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String sendOrder(@RequestParam("carId") Integer id, @AuthenticationPrincipal User userSec, Model model) {
        model.addAttribute("car", carService.findCarById(id));
        model.addAttribute("user", userService.findUserByUserName(userSec.getUsername()));
        model.addAttribute("order", new OrderDto());
        return "order/order-add";
    }

    @PostMapping("add")
    public String addOrder(@Validated @ModelAttribute("order") OrderDto orderDto, BindingResult bindingResult
            , Model model) {

//        if (carModelService.findModelByName(carModelDto.getName()) != null) {
//            bindingResult
//                    .rejectValue("name", "error.carModelDto",
//                            "There is already a model with the model name provided");
//            model.addAttribute("brands", brandService.getAll());
//            return "model/model-add";
//        }
//        if (carModelDto.getBrand().getId() == null) {
//            model.addAttribute("brandError", "Please, provide not empty brand");
//            model.addAttribute("brands", brandService.getAll());
//            return "model/model-add";
//        }
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("brands", brandService.getAll());
//            return "model/model-add";
//        }
//        carModelService.saveModel(carModelDto);
        return "redirect:/models";
    }
}
