package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerById(Integer id);

    Customer findCustomerByEmail(String email);

    boolean existsById(Integer id);
}
