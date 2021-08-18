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

    @Query("SELECT i from Invoiсe i where "
            + "(i.message like %?1%"
            + "or concat(i.order.user.userName,'')  like %?1% "
            + "or concat(i.order.id,'')  like %?1% "
            + "or concat(i.total,'')  like %?1% ) order by i.id")
    List<Invoiсe> searchAll(String keyword);

    @Query("SELECT i from Invoiсe i where (i.order.user = ?1)"
            + " AND (?2 is null or i.message like %?2%"
            + "or concat(i.order.id,'')  like %?2% "
            + "or concat(i.total,'')  like %?2% ) order by i.id")
    List<Invoiсe> searchAllByUser(User user, String keyword);

    @Query("SELECT i from Invoiсe i where i.order.user = ?1")
    List<Invoiсe> findAllByUser(User user);

//    List<Invoiсe> findAllByUser(User user);

    boolean existsById(Integer id);
}
