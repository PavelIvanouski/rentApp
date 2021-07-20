package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    Type findTypeById(Integer id);

    Type findTypeByName(String name);

    boolean existsById(Integer id);
}
