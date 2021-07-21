package by.academy.rentApp.service;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();

    OrderDto saveOrder(OrderDto orderDto);

    OrderDto findOrderById(Integer id);

    void  deleteOrder(OrderDto orderDto);

    boolean existsById(Integer id);

}
