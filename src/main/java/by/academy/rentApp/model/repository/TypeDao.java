package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDao extends JpaRepository<Type, Integer> {
}
