package by.academy.rentApp.service;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.model.entity.User;

import java.time.OffsetDateTime;
import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();

    List<OrderDto> getAllByUser(UserFormDto userFormDto);

    OrderDto saveOrder(OrderDto orderDto);

    OrderDto findOrderById(Integer id);

    List<OrderDto> findCurrentOrders(Integer id, List<String> statuses, OffsetDateTime rentBegin, OffsetDateTime renEnd);

    void  deleteOrder(OrderDto orderDto);

    boolean existsById(Integer id);

}
