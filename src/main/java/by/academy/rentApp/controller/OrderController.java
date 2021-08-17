package by.academy.rentApp.controller;

import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.exception.AppException;
import by.academy.rentApp.service.*;
import by.academy.rentApp.service.impl.UserServiceImpl;
import by.academy.rentApp.util.DatesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;


@Controller
//@RequestMapping("/orders")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final CarService carService;
    private final UserServiceImpl userService;
    private final StatusService statusService;
    private final InvoiceService invoiceService;
    private static final String STATUS_INVOICED = "invoiced";
    private static final String STATUS_BOOKED = "booked";
    private static final String STATUS_CLOSED = "closed";
    private static final String STATUS_DENIED = "denied";
    private static final String STATUS_CANCELLED = "cancelled";
    private static final List<String> RENTED_CARS_STATUSES = List.of(STATUS_INVOICED, STATUS_BOOKED);
    private static final List<String> USER_ORDER_STATUSES = List.of(STATUS_BOOKED, STATUS_CANCELLED);
    private static final List<String> ADMIN_BOOKED_ORDER_STATUSES = List
            .of(STATUS_INVOICED, STATUS_BOOKED, STATUS_DENIED);
    private static final List<String> ADMIN_INVOICED_ORDER_STATUSES = List.of(STATUS_INVOICED, STATUS_CLOSED);


    public OrderController(OrderService orderService, CarService carService, UserServiceImpl userService, StatusService statusService, InvoiceService invoiceService) {
        this.orderService = orderService;
        this.carService = carService;
        this.userService = userService;
        this.statusService = statusService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/orders/add")
    public String getAddOrderForm(@RequestParam("carId") Integer id, @AuthenticationPrincipal User userSec, Model model) throws AppException {
        if (!carService.existsById(id)) {
            throw new AppException("No car found");
//            return "redirect:/cars/all";
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
            , Model model) throws AppException {

        if (!DatesUtil.chekDates(rBegin, rEnd) || currentOffSet == null) {
            throw new AppException("Invalid dates");
//            model.addAttribute("datesMessage", "Invalid data");
//            return "order/order-add";
        }
        if (orderDto.getUser().getPassport() == null || "".equals(orderDto.getUser().getPassport())) {
            model.addAttribute("datesMessage", "Invalid data");
            return "order/order-add";
        }
        orderDto.setRentBegin(OffsetDateTime.of(rBegin, ZoneOffset.of(currentOffSet)));
        orderDto.setRentEnd(OffsetDateTime.of(rEnd, ZoneOffset.of(currentOffSet)));

        if (bindingResult.hasErrors()) {
            return "order/order-add";
        }

        List<OrderDto> currentOrders = orderService.findCurrentOrders(orderDto.getCar().getId()
                , RENTED_CARS_STATUSES, orderDto.getRentBegin(), orderDto.getRentEnd());
        if (currentOrders.size() != 0) {
            String currentOrdersMessage = "Car is already booked: ";
            model.addAttribute("currentOrdersMessage", currentOrdersMessage);
            model.addAttribute("currentOrders", currentOrders);
            return "order/order-add";
        }

        StringBuilder stringBuilderBefore = new StringBuilder();
        stringBuilderBefore.append("Save order from ")
                .append(orderDto.getRentBegin()).append(" to ").append(orderDto.getRentEnd());
        LOGGER.debug(stringBuilderBefore.toString());

        OrderDto savedOrder = orderService.saveOrder(orderDto);

        StringBuilder stringBuilderAfter = new StringBuilder();
        stringBuilderAfter.append("Result order id=").append(savedOrder.getId())
                .append(" with total ").append(savedOrder.getTotal());
        LOGGER.debug(stringBuilderAfter.toString());
        return "redirect:/user/orders/" + savedOrder.getId();
    }

    @GetMapping("user/orders")
    String getUserOrders(@AuthenticationPrincipal User userSec, Model model) {
        model.addAttribute("statuses", statusService.getAllByNameList(USER_ORDER_STATUSES));
        model.addAttribute("orders", orderService
                .getAllByUser(userService.findUserByUserName(userSec.getUsername())));
        return "order/orders-user";
    }


    @GetMapping("user/orders/{id}")
    public String getUserOrderForm(@PathVariable Integer id, Model model) throws AppException {
        if (!orderService.existsById(id)) {
            throw new AppException("No order with id =" + id);
//            return "redirect:/user/orders";
        }
        OrderDto usersOrder = orderService.findOrderById(id);
        if (STATUS_BOOKED.equals(usersOrder.getStatus().getName())) {
            model.addAttribute("statuses", statusService.getAllByNameList(USER_ORDER_STATUSES));
            model.addAttribute("allowEdit", true);
        }

        if (STATUS_INVOICED.equals(usersOrder.getStatus().getName())
                || STATUS_CLOSED.equals(usersOrder.getStatus().getName())) {
            addInvoicedButtons(model, invoiceService.getAllByOrder(usersOrder));
        }

        model.addAttribute("order", usersOrder);
        return "order/order-user";
    }


    @PostMapping("user/orders/{id}")
    public String saveUserOrder(@ModelAttribute("order") OrderDto orderDto, Model model, BindingResult bindingResult) throws AppException {
        OrderDto orderBeforeUpdating = orderService.findOrderById(orderDto.getId());
        orderDto.setCreatingDate(orderBeforeUpdating.getCreatingDate());
        orderDto.setRentBegin(orderBeforeUpdating.getRentBegin());
        orderDto.setRentEnd(orderBeforeUpdating.getRentEnd());


        if (!STATUS_BOOKED.equals(orderBeforeUpdating.getStatus().getName())
                || STATUS_BOOKED.equals(orderDto.getStatus().getName())) {
            throw new AppException("Order id=" + orderDto.getId() + " already booked");
//            model.addAttribute("statuses", statusService.getAllByNameList(USER_ORDER_STATUSES));
//            model.addAttribute("allowEdit", true);
//            bindingResult
//                    .rejectValue("status", "error.orderDto",
//                            "Order is already booked");
//            return "order/order-user";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", statusService.getAllByNameList(USER_ORDER_STATUSES));
            model.addAttribute("allowEdit", true);
            return "order/order-user";
        }
        LOGGER.debug("orderService.saveOrder called for " + orderDto);
        OrderDto savedOrder = orderService.saveOrder(orderDto);
        LOGGER.debug("Order edited " + savedOrder);
        return "redirect:/user/orders/" + savedOrder.getId();
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
        if (STATUS_BOOKED.equals(usersOrder.getStatus().getName())) {
            model.addAttribute("statuses", statusService.getAllByNameList(ADMIN_BOOKED_ORDER_STATUSES));
            model.addAttribute("allowEdit", true);
        }

        if (STATUS_INVOICED.equals(usersOrder.getStatus().getName())) {
            model.addAttribute("statuses", statusService.getAllByNameList(ADMIN_INVOICED_ORDER_STATUSES));
            model.addAttribute("allowEdit", true);
        }

        if (STATUS_DENIED.equals(usersOrder.getStatus().getName())) {
            model.addAttribute("orderDenied", true);
        }

        if (STATUS_INVOICED.equals(usersOrder.getStatus().getName())
                || STATUS_CLOSED.equals(usersOrder.getStatus().getName())) {
            addInvoicedButtons(model, invoiceService.getAllByOrder(usersOrder));
        }

        model.addAttribute("order", usersOrder);
        return "order/order-admin";
    }


    @PostMapping("admin/orders/{id}")
    public String saveAdminOrder(@ModelAttribute("order") OrderDto orderDto
            , Model model
            , @RequestParam(value = "extraMessage", required = false) String extraMessage
            , @RequestParam(value = "extraTotal", required = false) Double extraTotal
            , BindingResult bindingResult) throws AppException {
        OrderDto orderBeforeUpdating = orderService.findOrderById(orderDto.getId());
        orderDto.setCreatingDate(orderBeforeUpdating.getCreatingDate());
        orderDto.setRentBegin(orderBeforeUpdating.getRentBegin());
        orderDto.setRentEnd(orderBeforeUpdating.getRentEnd());

        if (!STATUS_BOOKED.equals(orderBeforeUpdating.getStatus().getName())
                && !STATUS_INVOICED.equals(orderBeforeUpdating.getStatus().getName())) {
            throw new AppException("Order status error");
//            return "redirect:/admin/orders";
        }

        if (STATUS_DENIED.equals(orderDto.getStatus().getName()) && orderDto.getMessage().length() == 0) {
//            model.addAttribute("statuses", statusService.getAllByNameList(ADMIN_BOOKED_ORDER_STATUSES));
//            model.addAttribute("allowEdit", true);
//            bindingResult
//                    .rejectValue("message", "error.orderDto",
//                            "Message should not  be empty for а denied order");
//            return "order/order-admin";
            throw new AppException("Empty message for denied order");
        }

        if (STATUS_BOOKED.equals(orderBeforeUpdating.getStatus().getName())
                || STATUS_BOOKED.equals(orderDto.getStatus().getName())) {
            throw new AppException("Order id=" + orderDto.getId() + " already booked");

        }
//        if (orderBeforeUpdating.getStatus().getId() == 2 && orderDto.getStatus().getId() == 2) {
//            model.addAttribute("statuses", statusService.getAllByNameList(ADMIN_BOOKED_ORDER_STATUSES));
//            model.addAttribute("allowEdit", true);
//            bindingResult
//                    .rejectValue("status", "error.orderDto",
//                            "Order is already booked");
//            return "order/order-admin";
//        }

        if (STATUS_INVOICED.equals(orderDto.getStatus().getName())
                || STATUS_CLOSED.equals(orderDto.getStatus().getName())) {
            addInvoicedButtons(model, invoiceService.getAllByOrder(orderDto));
        }

        if (STATUS_INVOICED.equals(orderBeforeUpdating.getStatus().getName())
                && STATUS_INVOICED.equals(orderDto.getStatus().getName())) {
            throw new AppException("Order id=" + orderDto.getId() + " already invoiced");

        }
//        if (orderBeforeUpdating.getStatus().getId() == 1 && orderDto.getStatus().getId() == 1) {
//            model.addAttribute("statuses", statusService.getAllByNameList(ADMIN_INVOICED_ORDER_STATUSES));
//            model.addAttribute("allowEdit", true);
//            bindingResult
//                    .rejectValue("status", "error.orderDto",
//                            "Order is already invoised");
//
//            return "order/order-admin";
//        }

        if (orderDto.getStatus().getId() == 1) {
            InvoiсeDto invoiсeDto1 = new InvoiсeDto();
            invoiсeDto1.setSerialNumber(1);
            invoiсeDto1.setTotal(orderDto.getTotal());
            invoiсeDto1.setOrder(orderDto);
//            invoiсeDto1.setUser(orderDto.getUser());
            invoiсeDto1.setMessage("rent payment");
            invoiceService.saveInvoice(invoiсeDto1);
        }

        if (orderDto.getStatus().getId() == 3) {
            if ((extraTotal != 0 && extraMessage == "")
                    || (extraTotal == 0 && extraMessage != "")) {
                model.addAttribute("extraError", "incorrect data");
                model.addAttribute("statuses", statusService.getAllByNameList(ADMIN_INVOICED_ORDER_STATUSES));
                model.addAttribute("allowEdit", true);
                return "order/order-admin";
            }
            if (extraTotal != 0 && extraMessage != "") {
                InvoiсeDto invoiсeDto2 = new InvoiсeDto();
                invoiсeDto2.setSerialNumber(2);
                invoiсeDto2.setTotal(extraTotal);
                invoiсeDto2.setOrder(orderDto);
//                invoiсeDto2.setUser(orderDto.getUser());
                invoiсeDto2.setMessage(extraMessage);
                invoiceService.saveInvoice(invoiсeDto2);
            }

        }
        LOGGER.debug("orderService.saveOrder called for " + orderDto);
        OrderDto savedOrder = orderService.saveOrder(orderDto);
        LOGGER.debug("Order edited " + savedOrder);
        return "redirect:/admin/orders/" + savedOrder.getId();
    }
    
    public void addInvoicedButtons(Model model, List<InvoiсeDto> ordersInvoices) {
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
}
