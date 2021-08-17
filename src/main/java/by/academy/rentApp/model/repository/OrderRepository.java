package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Order;
import by.academy.rentApp.model.entity.User;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderById(Integer id);


    List<Order> findAllByUser(User user);

    @Query("SELECT o FROM Order o WHERE o.car.id = :carId and o.status.name IN :statuses and " +
            "((o.rentBegin >= :rentBegin and o.rentBegin <= :rentEnd) " +
            "or (o.rentEnd >= :rentBegin and o.rentEnd <= :rentEnd)" +
            "or (o.rentBegin <= :rentBegin and o.rentEnd >= :rentEnd))")
    List<Order> findOrderByCarAndStatusAndDates(
            @Param("carId") Integer id,
            @Param("statuses") List<String> statuses,
            @Param("rentBegin") OffsetDateTime rentBegin,
            @Param("rentEnd") OffsetDateTime rentEnd);

    boolean existsById(Integer id);
}
