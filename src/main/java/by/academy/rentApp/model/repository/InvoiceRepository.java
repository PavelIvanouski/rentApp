package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Invoiсe;
import by.academy.rentApp.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoiсe, Integer> {
    Invoiсe findInvoiсeById(Integer id);

//    Invoiсe findInvoiсeByOrderAndAndSerialNumber(Order order, Integer serialNumber);

    List<Invoiсe> findAllByOrder(Order order);

    boolean existsById(Integer id);
}
