package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> {

    Messages findMessagesById(Integer id);

    boolean existsById(Integer id);
}
