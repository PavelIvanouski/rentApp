package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    @Query("SELECT s FROM Status s WHERE s.name  IN :nameList")
    List<Status> findAllByNameList(@Param("nameList") List<String> nameList);

    Status findStatusById(Integer id);

    Status findStatusByName(String name);

    boolean existsById(Integer id);
}
