package by.academy.rentApp.mapper;


import by.academy.rentApp.dto.RoleDto;
import by.academy.rentApp.dto.TypeDto;
import by.academy.rentApp.model.entity.Role;
import by.academy.rentApp.model.entity.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RoleMapperImpl.class})
public class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testRoleToRoleDto() {

        Role role = new Role();
        role.setId(1);
        role.setRole("Admin");
        RoleDto roleDto = roleMapper.roleToRoleDto(role);
        assertThat(roleDto).isNotNull();
        assertThat(roleDto).hasFieldOrPropertyWithValue("id", 1);
        assertThat(roleDto).hasFieldOrPropertyWithValue("name", "Admin");

    }

    @Test
    public void testRoleDtoToRole() {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("ADMIN");
        Role role = roleMapper.roleDtoToRole(roleDto);
        assertThat(role).isNotNull();
        assertThat(role).hasFieldOrPropertyWithValue("role", "ADMIN");

    }

}
