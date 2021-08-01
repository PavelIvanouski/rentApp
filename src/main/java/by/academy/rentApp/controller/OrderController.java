package by.academy.rentApp.controller;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.service.CarService;
import by.academy.rentApp.service.OrderService;
import by.academy.rentApp.service.UserService;
import by.academy.rentApp.util.DatesUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;


@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
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

    @GetMapping("/add")
    public String addOrder(@RequestParam("carId") Integer id, @AuthenticationPrincipal User userSec, Model model) {
        if (!carService.existsById(id)) {
            return "redirect:/cars/all";
        }
        CarDto car = carService.findCarById(id);
//        model.addAttribute("user", userService.findUserByUserName(userSec.getUsername()));
        OrderDto order = new OrderDto();
        order.setCar(car);
        order.setUser(userService.findUserByUserName(userSec.getUsername()));
        model.addAttribute("order", order);
        return "order/order-add";
    }

//    @PostMapping("add")
//    public String addOrder(@Validated @ModelAttribute("order") OrderDto orderDto
//            , @RequestParam("rentBegin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rentBegin
//            , @RequestParam("rentEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rentEnd
//            , BindingResult bindingResult
//            , Model model) {
//
////        if (carModelService.findModelByName(carModelDto.getName()) != null) {
////            bindingResult
////                    .rejectValue("name", "error.carModelDto",
////                            "There is already a model with the model name provided");
////            model.addAttribute("brands", brandService.getAll());
////            return "model/model-add";
////        }
////        if (carModelDto.getBrand().getId() == null) {
////            model.addAttribute("brandError", "Please, provide not empty brand");
////            model.addAttribute("brands", brandService.getAll());
////            return "model/model-add";
////        }
////        if (bindingResult.hasErrors()) {
////            model.addAttribute("brands", brandService.getAll());
////            return "model/model-add";
////        }
////        carModelService.saveModel(carModelDto);
//        return "redirect:/models";
//    }

    @PostMapping("add")
    public String addOrder(@Validated @ModelAttribute("order") OrderDto orderDto
            , @RequestParam(value = "rBegin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rBegin
            , @RequestParam(value = "rEnd", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rEnd
            , BindingResult bindingResult
            , Model model) {

        if (!DatesUtil.chekDates(rBegin, rEnd)) {
            return "order/order-add";
        }
        ZoneId zoneId = ZoneId.systemDefault();
        OffsetDateTime rentBegin = rBegin.atZone(zoneId).toOffsetDateTime();
        orderDto.setRentBegin(rentBegin);
        OffsetDateTime rentEnd = rEnd.atZone(zoneId).toOffsetDateTime();
        orderDto.setRentEnd(rentEnd);
        if (bindingResult.hasErrors()) {
            return "order/order-add";
        }
//        carModelService.saveModel(carModelDto);

        orderService.saveOrder(orderDto);
        return "order/orders-user";
    }
}
