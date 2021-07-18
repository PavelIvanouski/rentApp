package by.academy.rentApp.model.repository;

import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleById(Integer id);

    Role findByRole(String role);

    boolean existsById(Integer id);
}
