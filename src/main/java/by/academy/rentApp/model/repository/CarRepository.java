package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
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

    @Query("SELECT c FROM Car c WHERE  "
            + "(?1 is null or c.model.id = ?1)"
            + " AND (?2 is null or c.type.id = ?2)"
            + " AND (?3 is null or c.engine.id = ?3)")
    List<Car> search(Integer modelId, Integer typeId, Integer engineId);


    //    @Query("SELECT c FROM Car c left join fetch c.orderList as o where o.car is null")
    @Query("SELECT c FROM Car c where c NOT in (SELECT o.car FROM Order o WHERE" +
            "((o.rentBegin >= ?1 and o.rentBegin <= ?2) " +
            "or (o.rentEnd >= ?1 and o.rentEnd <= ?2)" +
            "or (o.rentBegin <= ?1 and o.rentEnd >= ?2))) and " +
            "(?3 is null or c.model.id = ?3) and " +
            "(?4 is null or c.type.id = ?4) and " +
            "(?5 is null or c.engine.id = ?5)")
    List<Car> searchAviable(OffsetDateTime rentBegin
            , OffsetDateTime rentEnd
            , Integer modelId, Integer typeId, Integer engineId);

    boolean existsById(Integer id);
}
