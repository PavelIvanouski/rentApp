package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    Status findStatusById(Integer id);

    Status findStatusByName(String name);

    boolean existsById(Integer id);
}
