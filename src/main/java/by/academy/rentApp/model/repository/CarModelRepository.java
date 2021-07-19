package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {

    CarModel findCarModelById(Integer id);

    CarModel findCarModelByName(String name);

    boolean existsById(Integer id);
}
