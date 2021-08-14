package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Invoiсe;
import by.academy.rentApp.model.entity.Order;
import by.academy.rentApp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoiсe, Integer> {
    Invoiсe findInvoiсeById(Integer id);

    List<Invoiсe> findAllByOrder(Order order);

    @Query("SELECT i from Invoiсe i where i.order.user = ?1")
    List<Invoiсe> findAllByUser(User user);

//    List<Invoiсe> findAllByUser(User user);

    boolean existsById(Integer id);
}
