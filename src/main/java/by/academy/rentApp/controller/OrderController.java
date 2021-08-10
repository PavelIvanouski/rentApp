package by.academy.rentApp.controller;

import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.service.*;
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
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


@Controller
//@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CarService carService;
    private final UserService userService;
    private final StatusService statusService;
    private final InvoiceService invoiceService;

    public OrderController(OrderService orderService, CarService carService, UserService userService, StatusService statusService, InvoiceService invoiceService) {
        this.orderService = orderService;
        this.carService = carService;
        this.userService = userService;
        this.statusService = statusService;
        this.invoiceService = invoiceService;
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
            , @RequestParam(value = "currentOffSet", required = false) String currentOffSet
            , BindingResult bindingResult
            , Model model) {

        if (!DatesUtil.chekDates(rBegin, rEnd) || currentOffSet == null) {
            return "order/order-add";
        }
        orderDto.setRentBegin(OffsetDateTime.of(rBegin, ZoneOffset.of(currentOffSet)));
        orderDto.setRentEnd(OffsetDateTime.of(rEnd, ZoneOffset.of(currentOffSet)));

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

        return "redirect:/user/orders/" + orderService.saveOrder(orderDto).getId();
    }

    @GetMapping("user/orders")
    String getUserOrders(@AuthenticationPrincipal User userSec, Model model) {
        List<Integer> idList = new ArrayList<>();
        idList.add(2);
        idList.add(5);
        model.addAttribute("statuses", statusService.getAllByIdList(idList));
        model.addAttribute("orders", orderService.getAllByUser(userService.findUserByUserName(userSec.getUsername())));
        return "order/orders-user";
    }


    @GetMapping("user/orders/{id}")
    public String getUserOrderForm(@PathVariable Integer id, Model model) {
        if (!orderService.existsById(id)) {
            return "redirect:/user/orders";
        }
        OrderDto usersOrder = orderService.findOrderById(id);
        if (usersOrder.getStatus().getId() == 2) {
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
    public String saveUserOrder(@ModelAttribute("order") OrderDto orderDto, Model model, BindingResult bindingResult) {
        OrderDto orderBeforeUpdating = orderService.findOrderById(orderDto.getId());
        orderDto.setCreatingDate(orderBeforeUpdating.getCreatingDate());
        orderDto.setRentBegin(orderBeforeUpdating.getRentBegin());
        orderDto.setRentEnd(orderBeforeUpdating.getRentEnd());
        List<Integer> idList = new ArrayList<>();
        idList.add(2);
        idList.add(5);

        if (orderBeforeUpdating.getStatus().getId() != 2
                || orderDto.getStatus().getId() == 2) {

            model.addAttribute("statuses", statusService.getAllByIdList(idList));
            model.addAttribute("allowEdit", true);
            bindingResult
                    .rejectValue("status", "error.orderDto",
                            "Order is already booked");
            return "order/order-user";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", statusService.getAllByIdList(idList));
            model.addAttribute("allowEdit", true);
            return "order/order-user";
        }
        return "redirect:/user/orders/" + orderService.saveOrder(orderDto).getId();
    }

    @GetMapping("admin/orders")
    String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "order/orders-admin";
    }

    @GetMapping("admin/orders/{id}")
    public String getAdminOrderForm(@PathVariable Integer id, Model model) {
        if (!orderService.existsById(id)) {
            return "redirect:/admin/orders";
        }
        OrderDto usersOrder = orderService.findOrderById(id);
        if (usersOrder.getStatus().getId() == 2) {
            List<Integer> idList = new ArrayList<>();
            idList.add(1);
            idList.add(2);
            idList.add(4);
            model.addAttribute("statuses", statusService.getAllByIdList(idList));
            model.addAttribute("allowEdit", true);
        }

        if (usersOrder.getStatus().getId() == 1) {
            List<Integer> idList = new ArrayList<>();
            idList.add(1);
            idList.add(3);
            model.addAttribute("statuses", statusService.getAllByIdList(idList));
            model.addAttribute("allowEdit", true);
        }

        if (usersOrder.getStatus().getId() == 1 || usersOrder.getStatus().getId() == 3) {
            List<InvoiсeDto> ordersInvoices = invoiceService.getAllByOrder(usersOrder);
            if (ordersInvoices.size() != 0) {
                model.addAttribute("invoiced", true);
                for (InvoiсeDto invoiсeDto : ordersInvoices) {
                    if (invoiсeDto.getSerialNumber() == 1) {
                        model.addAttribute("mainInvoice", invoiсeDto.getId());
                    } else if (invoiсeDto.getSerialNumber() == 2) {
                        model.addAttribute("extraInvoice", invoiсeDto.getId());
                    }
                }
            }
        }


        model.addAttribute("order", usersOrder);
        return "order/order-admin";
    }

    @PostMapping("admin/orders/{id}")
    public String saveAdminOrder(@ModelAttribute("order") OrderDto orderDto
            , Model model
            , @RequestParam(value = "extraMessage", required = false) String extraMessage
            , @RequestParam(value = "extraTotal", required = false) String extraTotal
            , BindingResult bindingResult) {
        OrderDto orderBeforeUpdating = orderService.findOrderById(orderDto.getId());
        orderDto.setCreatingDate(orderBeforeUpdating.getCreatingDate());
        orderDto.setRentBegin(orderBeforeUpdating.getRentBegin());
        orderDto.setRentEnd(orderBeforeUpdating.getRentEnd());

        List<Integer> idListBooked = new ArrayList<>();
        idListBooked.add(1);
        idListBooked.add(2);
        idListBooked.add(4);

        List<Integer> idListInvoised = new ArrayList<>();
        idListInvoised.add(1);
        idListInvoised.add(3);

        if (orderBeforeUpdating.getStatus().getId() != 2
                && orderBeforeUpdating.getStatus().getId() != 1) {
            return "redirect:/admin/orders";
        }

        if (orderDto.getStatus().getId() == 4 && orderDto.getMessage().length() == 0) {
            model.addAttribute("statuses", statusService.getAllByIdList(idListBooked));
            model.addAttribute("allowEdit", true);
            bindingResult
                    .rejectValue("message", "error.orderDto",
                            "Message should not  be empty for а denied order");
            return "order/order-admin";
        }

        if (orderBeforeUpdating.getStatus().getId() == 2 && orderDto.getStatus().getId() == 2) {
            model.addAttribute("statuses", statusService.getAllByIdList(idListBooked));
            model.addAttribute("allowEdit", true);
            bindingResult
                    .rejectValue("status", "error.orderDto",
                            "Order is already booked");
            return "order/order-admin";
        }

        if (orderDto.getStatus().getId() == 1 && orderDto.getStatus().getId() == 3) {
            List<InvoiсeDto> ordersInvoices = invoiceService.getAllByOrder(orderDto);
            if (ordersInvoices.size() != 0) {
                model.addAttribute("invoiced", true);
                for (InvoiсeDto invoiсeDto : ordersInvoices) {
                    if (invoiсeDto.getSerialNumber() == 1) {
                        model.addAttribute("mainInvoice", invoiсeDto.getId());
                    } else if (invoiсeDto.getSerialNumber() == 2) {
                        model.addAttribute("extraInvoice", invoiсeDto.getId());
                    }
                }
            }
        }

        if (orderBeforeUpdating.getStatus().getId() == 1 && orderDto.getStatus().getId() == 1) {
            model.addAttribute("statuses", statusService.getAllByIdList(idListInvoised));
            model.addAttribute("allowEdit", true);
            List<Integer> idList = new ArrayList<>();
            idList.add(1);
            idList.add(3);
            model.addAttribute("statuses", statusService.getAllByIdList(idList));
            model.addAttribute("allowEdit", true);

            bindingResult
                    .rejectValue("status", "error.orderDto",
                            "Order is already invoised");

            return "order/order-admin";
        }

        if (orderDto.getStatus().getId() == 1) {
            InvoiсeDto invoiсeDto = new InvoiсeDto();
            invoiсeDto.setSerialNumber(1);
            invoiсeDto.setTotal(orderDto.getTotal());
            invoiсeDto.setOrder(orderDto);
            invoiсeDto.setMessage("rent payment");
            invoiceService.saveInvoice(invoiсeDto);
        }

        if (orderDto.getStatus().getId() == 3) {


        }

        return "redirect:/admin/orders/" + orderService.saveOrder(orderDto).getId();
    }
}
