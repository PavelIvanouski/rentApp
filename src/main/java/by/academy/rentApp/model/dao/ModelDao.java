package by.academy.rentApp.model.dao;

import by.academy.rentApp.model.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelDao extends JpaRepository<Model, Integer> {
    Model findModelById(Integer id);
}
