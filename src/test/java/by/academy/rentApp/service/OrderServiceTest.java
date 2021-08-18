package by.academy.rentApp.service;

import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.model.entity.Order;
import by.academy.rentApp.model.entity.User;
import by.academy.rentApp.model.repository.OrderRepository;
import by.academy.rentApp.model.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void testFindOrderById() {

        Order order = new Order();
        order.setId(1);
        order.setPrice(Integer.valueOf("10"));
        given(this.orderRepository.findOrderById(any()))
                .willReturn(order);
        OrderDto orderDto = orderService.findOrderById(1);
        assertThat(orderDto).isNotNull();
        assertThat(orderDto.getId()).isEqualTo(1);
        assertThat(orderDto.getPrice()).isEqualTo(Integer.valueOf("10"));

    }

//    @Test
//    public void testGetAll() {
//
//        List<Order> orders = new ArrayList<>();
//        orders.add(new Order());
//        orders.add(new Order());
//        orders.add(new Order());
//        given(this.orderRepository.findAll())
//                .willReturn(orders);
//        List<OrderDto> orderDtos = orderService.getAll();
//        assertThat(orderDtos.size()).isEqualTo(3);
//
//    }

    @Test
    public void testExistById() {

        given(this.orderRepository.existsById(any())).willReturn(true);
        assertThat(orderRepository.existsById(10)).isTrue();

    }
//
    @Test
    public void testSaveOrder() {

        Order order = new Order();
        order.setId(1);
        order.setPrice(Integer.valueOf("10"));
        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);
        OrderDto newOrderDto = new OrderDto();
        newOrderDto.setId(1);
        newOrderDto.setPrice(Integer.valueOf("10"));
        OrderDto savedOrderDto = orderService.saveOrder(newOrderDto);
        assertThat(savedOrderDto.getPrice()).isSameAs(newOrderDto.getPrice());

    }
}

