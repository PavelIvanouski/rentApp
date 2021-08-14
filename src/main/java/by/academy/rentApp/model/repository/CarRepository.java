package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Car findCarById(Integer id);

    @Query("SELECT c FROM Car c WHERE c.model.brand.name LIKE %?1%"
            + " OR c.model.name LIKE %?1%"
            + " OR c.type.name LIKE %?1%"
            + " OR c.engine.name LIKE %?1%"
            + " OR c.year LIKE %?1%"
            + " OR CONCAT(c.engineVolume,'') LIKE %?1%")
    List<Car> search(String keyword);

    boolean existsById(Integer id);
}
