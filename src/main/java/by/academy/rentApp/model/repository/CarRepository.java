package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Car findCarById(Integer id);

    boolean existsById(Integer id);
}
