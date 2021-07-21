package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CarMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto orderToOrderDto(Order order);

    Order orderDtoToOrder(OrderDto orderDto);
}
