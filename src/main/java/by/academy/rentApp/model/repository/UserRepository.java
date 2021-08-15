package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByUserName(String userName);

    User findUserById(Integer id);

    @Query("SELECT u FROM User u WHERE u.userName LIKE %?1%"
            + " OR u.email LIKE %?1%"
            + " OR u.firstName LIKE %?1%"
            + " OR u.lastName LIKE %?1%"
            + " OR u.passport LIKE %?1%")
    List<User> search(String keyword);
}
