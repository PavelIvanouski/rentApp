package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Car findCarById(Integer id);

//    @Query("SELECT c FROM Car c WHERE (c.model.brand.name LIKE %?1%"
//            + " OR c.model.name LIKE %?1%"
//            + " OR c.type.name LIKE %?1%"
//            + " OR c.engine.name LIKE %?1%"
//            + " OR c.year LIKE %?1%"
//            + " OR CONCAT(c.engineVolume,'') LIKE %?1%)"
//            + " AND (?2 is null or c.type.id = ?2)")
//    List<Car> search(String keyword, Integer typeId);

    @Query("SELECT c FROM Car c WHERE (?1 is null or c.model.id = ?1)"
            + " AND (?2 is null or c.type.id = ?2)"
            + " AND (?3 is null or c.type.id = ?3)")
    List<Car> search(Integer modelId, Integer typeId, Integer engineId);

    boolean existsById(Integer id);
}
