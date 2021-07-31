package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.mapper.CarMapper;
import by.academy.rentApp.mapper.OrderMapper;
import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Order;
import by.academy.rentApp.model.repository.CarRepository;
import by.academy.rentApp.model.repository.OrderRepository;
import by.academy.rentApp.service.CarService;
import by.academy.rentApp.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        orders.forEach(order -> {
            orderDtos.add(OrderMapper.INSTANCE.orderToOrderDto(order));
        });
        return orderDtos;
    }

    @Override
    @Transactional
    public OrderDto saveOrder(OrderDto orderDto) {
        if (orderDto.getId() == null) {
            orderDto.setCreatingDate(OffsetDateTime.now());



        } else {
//            orderDto.setUpdatingDate(sqlTimestamp);
            orderDto.setUpdatingDate(OffsetDateTime.now());
        }
        Order savedOrder = orderRepository.save(OrderMapper.INSTANCE.orderDtoToOrder(orderDto));
        return OrderMapper.INSTANCE.orderToOrderDto(savedOrder);
    }

    @Override
    public OrderDto findOrderById(Integer id) {
        Order order = orderRepository.findOrderById(id);
        return OrderMapper.INSTANCE.orderToOrderDto(order);
    }

    @Override
    public void deleteOrder(OrderDto orderDto) {
        orderRepository.delete(OrderMapper.INSTANCE.orderDtoToOrder(orderDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return orderRepository.existsById(id);
    }
}
