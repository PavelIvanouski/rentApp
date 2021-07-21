package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderById(Integer id);

    boolean existsById(Integer id);
}
