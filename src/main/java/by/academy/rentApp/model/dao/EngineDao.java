package by.academy.rentApp.model.dao;

import by.academy.rentApp.model.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineDao  extends JpaRepository<Engine, Integer> {
    Engine findEngineById(Integer id);

    Engine findEngineByName(String name);


}
