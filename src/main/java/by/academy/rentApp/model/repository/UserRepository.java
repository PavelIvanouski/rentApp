package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
        User findByEmail(String email);
        User findByUserName(String userName);
}
