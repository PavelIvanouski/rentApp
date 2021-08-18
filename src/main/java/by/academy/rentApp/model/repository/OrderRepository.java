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

    @Query("SELECT o FROM Order o WHERE (o.user = ?1)"
            + "AND (?2 is null or o.car.model.brand.name LIKE %?2%"
            + "or o.car.model.name LIKE %?2%"
            + "or CONCAT(o.total,'') LIKE %?2%)"
            + "AND (?3 is null or o.status.id = ?3) order by o.id")
    List<Order> searchAllByUser(User user, String keyword, Integer statusId);

    @Query("SELECT o FROM Order o WHERE o.car.id = :carId and o.status.id IN :statuses and " +
            "((o.rentBegin >= :rentBegin and o.rentBegin <= :rentEnd) " +
            "or (o.rentEnd >= :rentBegin and o.rentEnd <= :rentEnd)" +
            "or (o.rentBegin <= :rentBegin and o.rentEnd >= :rentEnd))")
    List<Order> findOrderByCarAndStatusAndDates(
            @Param("carId") Integer id,
            @Param("statuses") List<Integer> statuses,
            @Param("rentBegin") OffsetDateTime rentBegin,
            @Param("rentEnd") OffsetDateTime rentEnd);

    @Query("SELECT o FROM Order o WHERE "
            + "(?1 is null or o.user.userName LIKE %?1%"
            + " or o.car.model.brand.name LIKE %?1%"
            + "or o.car.model.name LIKE %?1%"
            + "or CONCAT(o.total,'') LIKE %?1%)"
            + "AND (?2 is null or o.status.id = ?2) order by o.id")
    List<Order> search(String keyword, Integer statusId);

    boolean existsById(Integer id);
}
