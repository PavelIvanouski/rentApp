package by.academy.rentApp.controller;

import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.dto.StatusDto;
import by.academy.rentApp.exception.AppException;
import by.academy.rentApp.service.*;
import by.academy.rentApp.service.impl.UserServiceImpl;
import by.academy.rentApp.util.DatesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
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
    private static final String ORDER = "order";
    private static final String ORDER_ID = "Order id=";
    private static final String ORDER_ADD = "order/order-add";
    private static final String STATUSES = "statuses";
    private static final Integer STATUS_INVOICED_ID = 1;
    private static final Integer STATUS_BOOKED_ID = 2;
    private static final Integer STATUS_CLOSED_ID = 3;
    private static final Integer STATUS_DENIED_ID = 4;
    private static final Integer STATUS_CANCELLED_ID = 5;
    private static final List<Integer> RENTED_CARS_STATUSES_ID = List.of(STATUS_INVOICED_ID, STATUS_BOOKED_ID);
    private static final List<Integer> USER_ORDER_STATUSES_ID = List.of(STATUS_BOOKED_ID, STATUS_CANCELLED_ID);
    private static final List<Integer> ADMIN_BOOKED_ORDER_STATUSES_ID = List
            .of(STATUS_INVOICED_ID, STATUS_BOOKED_ID, STATUS_DENIED_ID);
    private static final List<Integer> ADMIN_INVOICED_ORDER_STATUSES_ID = List.of(STATUS_INVOICED_ID, STATUS_CLOSED_ID);
    private static final List<Integer> ALL_ORDER_STATUSES_ID = List
            .of(STATUS_INVOICED_ID, STATUS_BOOKED_ID, STATUS_CLOSED_ID, STATUS_DENIED_ID, STATUS_CANCELLED_ID);


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
        model.addAttribute(ORDER, order);
        return ORDER_ADD;
    }

    @PostMapping("/orders/add")
    public String addOrder(@Validated @ModelAttribute(ORDER) OrderDto orderDto
            , @RequestParam(value = "rBegin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rBegin
            , @RequestParam(value = "rEnd", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rEnd
            , @RequestParam(value = "currentOffSet", required = false) String currentOffSet
            , BindingResult bindingResult
            , Model model) throws AppException {

        if (!DatesUtil.chekDates(rBegin, rEnd) || currentOffSet == null) {
            throw new AppException("Invalid dates");
//            model.addAttribute("datesMessage", "Invalid data");
//            return ORDER_ADD;
        }
        if (orderDto.getUser().getPassport() == null || "".equals(orderDto.getUser().getPassport())) {
            model.addAttribute("datesMessage", "Invalid data");
            return ORDER_ADD;
        }
        orderDto.setRentBegin(OffsetDateTime.of(rBegin, ZoneOffset.of(currentOffSet)));
        orderDto.setRentEnd(OffsetDateTime.of(rEnd, ZoneOffset.of(currentOffSet)));

        if (bindingResult.hasErrors()) {
            return ORDER_ADD;
        }

        List<OrderDto> currentOrders = orderService.findCurrentOrders(orderDto.getCar().getId()
                , RENTED_CARS_STATUSES_ID, orderDto.getRentBegin(), orderDto.getRentEnd());
        if (currentOrders.size() != 0) {
            String currentOrdersMessage = "Car is already booked: ";
            model.addAttribute("currentOrdersMessage", currentOrdersMessage);
            model.addAttribute("currentOrders", currentOrders);
            return ORDER_ADD;
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
    public String getUserOrders(@AuthenticationPrincipal User userSec, Model model
            , @Param("keyword") String keyword
            , @Param("statusId") Integer statusId) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("statusId", statusId);
        model.addAttribute(STATUSES, statusService.getAllByIdList(ALL_ORDER_STATUSES_ID));
        model.addAttribute("orders", orderService
                .getAllByUser(userService.findUserByUserName(userSec.getUsername()), keyword, statusId));
        return "order/orders-user";
    }


    @GetMapping("user/orders/{id}")
    public String getUserOrderForm(@PathVariable Integer id, Model model) throws AppException {
        if (!orderService.existsById(id)) {
            throw new AppException("No order with id =" + id);
//            return "redirect:/user/orders";
        }
        OrderDto usersOrder = orderService.findOrderById(id);
        if (STATUS_BOOKED_ID.equals(usersOrder.getStatus().getId())) {
            model.addAttribute(STATUSES, statusService.getAllByIdList(USER_ORDER_STATUSES_ID));
            model.addAttribute("allowEdit", true);
        }

        if (STATUS_INVOICED_ID.equals(usersOrder.getStatus().getId())
                || STATUS_CLOSED_ID.equals(usersOrder.getStatus().getId())) {
            addInvoicedButtons(model, invoiceService.getAllByOrder(usersOrder));
        }

        model.addAttribute(ORDER, usersOrder);
        return "order/order-user";
    }


    @PostMapping("user/orders/{id}")
    public String saveUserOrder(@ModelAttribute(ORDER) OrderDto orderDto, Model model, BindingResult bindingResult) throws AppException {
        OrderDto orderBeforeUpdating = orderService.findOrderById(orderDto.getId());
        orderDto.setCreatingDate(orderBeforeUpdating.getCreatingDate());
        orderDto.setRentBegin(orderBeforeUpdating.getRentBegin());
        orderDto.setRentEnd(orderBeforeUpdating.getRentEnd());


        if (STATUS_BOOKED_ID.equals(orderDto.getStatus().getId())) {
            throw new AppException(ORDER_ID + orderDto.getId() + " already booked");
//            model.addAttribute(STATUSES, statusService.getAllByNameList(USER_ORDER_STATUSES));
//            model.addAttribute("allowEdit", true);
//            bindingResult
//                    .rejectValue("status", "error.orderDto",
//                            "Order is already booked");
//            return "order/order-user";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute(STATUSES, statusService.getAllByIdList(USER_ORDER_STATUSES_ID));
            model.addAttribute("allowEdit", true);
            return "order/order-user";
        }
        LOGGER.debug("orderService.saveOrder called for " + orderDto);
        OrderDto savedOrder = orderService.saveOrder(orderDto);
        LOGGER.debug("Order edited " + savedOrder);
        return "redirect:/user/orders/" + savedOrder.getId();
    }

    @GetMapping("admin/orders")
    String getAllOrders(Model model, @Param("keyword") String keyword
            , @Param("statusId") Integer statusId) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("statusId", statusId);
        List<StatusDto> statusDtos = statusService.getAll();
        model.addAttribute("statuses", statusDtos);

        model.addAttribute("orders", orderService.getAll(keyword, statusId));
        return "order/orders-admin";
    }

    @GetMapping("admin/orders/{id}")
    public String getAdminOrderForm(@PathVariable Integer id, Model model) throws AppException {
        if (!orderService.existsById(id)) {
            throw new AppException("No order with id =" + id);
//            return "redirect:/admin/orders";
        }
        OrderDto usersOrder = orderService.findOrderById(id);
        if (STATUS_BOOKED_ID.equals(usersOrder.getStatus().getId())) {
            model.addAttribute(STATUSES, statusService.getAllByIdList(ADMIN_BOOKED_ORDER_STATUSES_ID));
            model.addAttribute("allowEdit", true);
        }

        if (STATUS_INVOICED_ID.equals(usersOrder.getStatus().getId())) {
            model.addAttribute(STATUSES, statusService.getAllByIdList(ADMIN_INVOICED_ORDER_STATUSES_ID));
            model.addAttribute("allowEdit", true);
        }

        if (STATUS_DENIED_ID.equals(usersOrder.getStatus().getId())) {
            model.addAttribute("orderDenied", true);
        }

        if (STATUS_INVOICED_ID.equals(usersOrder.getStatus().getId())
                || STATUS_CLOSED_ID.equals(usersOrder.getStatus().getId())) {
            addInvoicedButtons(model, invoiceService.getAllByOrder(usersOrder));
        }

        model.addAttribute(ORDER, usersOrder);
        return "order/order-admin";
    }


    @PostMapping("admin/orders/{id}")
    public String saveAdminOrder(@ModelAttribute(ORDER) OrderDto orderDto
            , Model model
            , @RequestParam(value = "extraMessage", required = false) String extraMessage
            , @RequestParam(value = "extraTotal", required = false) Double extraTotal
            , BindingResult bindingResult) throws AppException {
        OrderDto orderBeforeUpdating = orderService.findOrderById(orderDto.getId());
        orderDto.setCreatingDate(orderBeforeUpdating.getCreatingDate());
        orderDto.setRentBegin(orderBeforeUpdating.getRentBegin());
        orderDto.setRentEnd(orderBeforeUpdating.getRentEnd());

        if (!STATUS_BOOKED_ID.equals(orderBeforeUpdating.getStatus().getId())
                && !STATUS_INVOICED_ID.equals(orderBeforeUpdating.getStatus().getId())) {
            throw new AppException("Order status error");
//            return "redirect:/admin/orders";
        }

        if (STATUS_DENIED_ID.equals(orderDto.getStatus().getId()) && orderDto.getMessage().length() == 0) {
//            model.addAttribute(STATUSES, statusService.getAllByNameList(ADMIN_BOOKED_ORDER_STATUSES));
//            model.addAttribute("allowEdit", true);
//            bindingResult
//                    .rejectValue("message", "error.orderDto",
//                            "Message should not  be empty for а denied order");
//            return "order/order-admin";
            throw new AppException("Empty message for denied order");
        }

        if (STATUS_BOOKED_ID.equals(orderDto.getStatus().getId())) {
            throw new AppException(ORDER_ID + orderDto.getId() + " already booked");

        }
//        if (orderBeforeUpdating.getStatus().getId() == 2 && orderDto.getStatus().getId() == 2) {
//            model.addAttribute(STATUSES, statusService.getAllByNameList(ADMIN_BOOKED_ORDER_STATUSES));
//            model.addAttribute("allowEdit", true);
//            bindingResult
//                    .rejectValue("status", "error.orderDto",
//                            "Order is already booked");
//            return "order/order-admin";
//        }

        if (STATUS_INVOICED_ID.equals(orderDto.getStatus().getId())
                || STATUS_CLOSED_ID.equals(orderDto.getStatus().getId())) {
            addInvoicedButtons(model, invoiceService.getAllByOrder(orderDto));
        }

        if (STATUS_INVOICED_ID.equals(orderBeforeUpdating.getStatus().getId())
                && STATUS_INVOICED_ID.equals(orderDto.getStatus().getId())) {
            throw new AppException("Order id=" + orderDto.getId() + " already invoiced");

        }
//        if (orderBeforeUpdating.getStatus().getId() == 1 && orderDto.getStatus().getId() == 1) {
//            model.addAttribute(STATUSES, statusService.getAllByNameList(ADMIN_INVOICED_ORDER_STATUSES));
//            model.addAttribute("allowEdit", true);
//            bindingResult
//                    .rejectValue("status", "error.orderDto",
//                            "Order is already invoised");
//
//            return "order/order-admin";
//        }

        if (STATUS_INVOICED_ID.equals(orderDto.getStatus().getId())) {
            InvoiсeDto invoiсeDto1 = new InvoiсeDto();
            invoiсeDto1.setSerialNumber(1);
            invoiсeDto1.setTotal(orderDto.getTotal());
            invoiсeDto1.setOrder(orderDto);
            invoiсeDto1.setMessage("rent payment");
            invoiceService.saveInvoice(invoiсeDto1);
        }

        if (STATUS_CLOSED_ID.equals(orderDto.getStatus().getId())) {
            if ((extraTotal != 0 && extraMessage == "")
                    || (extraTotal == 0 && extraMessage != "")) {
                throw new AppException("Incorrect data for denied order");
//                model.addAttribute("extraError", "incorrect data");
//                model.addAttribute(STATUSES, statusService.getAllByIdList(ADMIN_INVOICED_ORDER_STATUSES_ID));
//                model.addAttribute("allowEdit", true);
//                return "order/order-admin";
            }
            if (extraTotal != 0 && extraMessage != "") {
                InvoiсeDto invoiсeDto2 = new InvoiсeDto();
                invoiсeDto2.setSerialNumber(2);
                invoiсeDto2.setTotal(extraTotal);
                invoiсeDto2.setOrder(orderDto);
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
