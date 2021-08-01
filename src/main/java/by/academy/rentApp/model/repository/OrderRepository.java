package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Order;
import by.academy.rentApp.model.entity.User;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderById(Integer id);

    
    List<Order> findAllByUser(User user);



    boolean existsById(Integer id);
}
