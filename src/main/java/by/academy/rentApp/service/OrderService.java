package by.academy.rentApp.service;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.OrderDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();

    OrderDto saveOrder(OrderDto orderDto);

    OrderDto findOrderById(Integer id);

    List<OrderDto> findCurrentOrders(Integer id, OffsetDateTime rentBegin, OffsetDateTime renEnd);

    void  deleteOrder(OrderDto orderDto);

    boolean existsById(Integer id);

}
