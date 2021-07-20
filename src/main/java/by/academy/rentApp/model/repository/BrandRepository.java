package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Brand findBrandById(Integer id);

    Brand findBrandByName(String name);

    boolean existsById(Integer id);
}
