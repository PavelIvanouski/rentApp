package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<User, Integer> {
    User findCustomerById(Integer id);

    User findCustomerByEmail(String email);

    boolean existsById(Integer id);
}
