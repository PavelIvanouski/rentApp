package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.mapper.OrderMapper;
import by.academy.rentApp.model.entity.Order;
import by.academy.rentApp.model.entity.User;
import by.academy.rentApp.model.repository.OrderRepository;
import by.academy.rentApp.model.repository.StatusRepository;
import by.academy.rentApp.service.OrderService;
import by.academy.rentApp.service.StatusService;
import by.academy.rentApp.util.DatesUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final StatusService statusService;

    public OrderServiceImpl(OrderRepository orderRepository, StatusRepository statusRepository, OrderMapper orderMapper, StatusService statusService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.statusService = statusService;
    }


    @Override
    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> {
            orderDtos.add(orderMapper.orderToOrderDto(order));
        });
        return orderDtos;
    }

    @Override
    public List<OrderDto> getAllByUser(User user) {
        List<Order> orders = orderRepository.findAllByUser(user);
        List<OrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> {
            orderDtos.add(orderMapper.orderToOrderDto(order));
        });
        return orderDtos;
    }

    @Override
    @Transactional
    public OrderDto saveOrder(OrderDto orderDto) {
        if (orderDto.getId() == null) {
            orderDto.setCreatingDate(OffsetDateTime.now());
            orderDto.setStatus(statusService.findStatusById(2));
            double hours = DatesUtil.returnDifferenceInHours(orderDto.getRentBegin(), orderDto.getRentEnd());
            double pricePerHour = Math.round((Double.valueOf(orderDto.getPrice()) / 24) * 100) / 100.00;
            orderDto.setTotal(Math.round((hours * pricePerHour) * 100) / 100.00);
        } else {
//            orderDto.setUpdatingDate(sqlTimestamp);
            orderDto.setUpdatingDate(OffsetDateTime.now());
        }
        Order savedOrder = orderRepository.save(orderMapper.orderDtoToOrder(orderDto));
        return orderMapper.orderToOrderDto(savedOrder);
    }

    @Override
    public OrderDto findOrderById(Integer id) {
        Order order = orderRepository.findOrderById(id);
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public List<OrderDto> findCurrentOrders(Integer id, List<Integer> statuses,OffsetDateTime rentBegin, OffsetDateTime rentEnd) {
        List<Order> orders = orderRepository.findOrderByCarAndStatusAndDates(id, statuses, rentBegin, rentEnd);
        List<OrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> {
            orderDtos.add(orderMapper.orderToOrderDto(order));
        });
        return orderDtos;
    }

    @Override
    public void deleteOrder(OrderDto orderDto) {
        orderRepository.delete(orderMapper.orderDtoToOrder(orderDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return orderRepository.existsById(id);
    }
}
