package by.academy.rentApp.controller;

import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.service.CarService;
import by.academy.rentApp.service.OrderService;
import by.academy.rentApp.service.StatusService;
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
import java.util.ArrayList;
import java.util.List;


@Controller
//@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CarService carService;
    private final UserService userService;
    private final StatusService statusService;

    public OrderController(OrderService orderService, CarService carService, UserService userService, StatusService statusService) {
        this.orderService = orderService;
        this.carService = carService;
        this.userService = userService;
        this.statusService = statusService;
    }

    @GetMapping("/orders/add")
    public String getAddOrderForm(@RequestParam("carId") Integer id, @AuthenticationPrincipal User userSec, Model model) {
        if (!carService.existsById(id)) {
            return "redirect:/cars/all";
        }
        OrderDto order = new OrderDto();
        order.setCar(carService.findCarById(id));
        order.setUser(userService.findUserByUserName(userSec.getUsername()));
        model.addAttribute("order", order);
        return "order/order-add";
    }

    @PostMapping("/orders/add")
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
        List<Integer> statuses = new ArrayList<>();
        statuses.add(1);
        statuses.add(2);
        List<OrderDto> currentOrders = orderService.findCurrentOrders(orderDto.getCar().getId()
                , statuses, orderDto.getRentBegin(), orderDto.getRentEnd());
        if (currentOrders.size() != 0) {
            String currentOrdersMessage = "Car is already booked: ";
            model.addAttribute("currentOrdersMessage", currentOrdersMessage);
            model.addAttribute("currentOrders", currentOrders);
            return "order/order-add";
        }

        OrderDto savedOrder = orderService.saveOrder(orderDto);

//        return "order/orders-user";
        return "redirect:/user/orders";
    }

    @GetMapping("user/orders")
    String getUserOrders(@AuthenticationPrincipal User userSec, Model model) {
        model.addAttribute("orders", orderService.getAllByUser(userService.findUserByUserName(userSec.getUsername())));
        return "order/orders-user";
    }

    @GetMapping("user/orders/{id}")
    public String getUserOrderForm(@PathVariable Integer id, Model model) {
        if (!orderService.existsById(id)) {
            return "redirect:/user/orders";
        }
        OrderDto usersOrder = orderService.findOrderById(id);
        if ("booked".equals(usersOrder.getStatus().getName())) {
            List<Integer> idList = new ArrayList<>();
            idList.add(2);
            idList.add(5);
            model.addAttribute("statuses", statusService.getAllByIdList(idList));
            model.addAttribute("allowEdit", true);
        }
        model.addAttribute("order", usersOrder);
        return "order/order-user";
    }

    @PostMapping("user/orders/{id}")
    public String saveUserOrder(@ModelAttribute("order") OrderDto orderDto, Model model) {
        if (orderDto == null) {
        }
//        if (!orderService.existsById(id)) {
//            return "redirect:/user/orders";
//        }
//        OrderDto usersOrder = orderService.findOrderById(id);
//        if ("booked".equals(usersOrder.getStatus().getName())) {
//            List<Integer> idList = new ArrayList<>();
//            idList.add(2);
//            idList.add(5);
//            model.addAttribute("statuses", statusService.getAllByIdList(idList));
//            model.addAttribute("allowEdit", true);
//        }
//        model.addAttribute("order", usersOrder);
//        return "order/order-user";
        return "redirect:/user/orders";
    }

    @GetMapping("admin/orders")
    String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "order/orders-admin";
    }
}
