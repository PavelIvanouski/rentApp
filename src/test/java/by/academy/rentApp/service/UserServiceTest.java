package by.academy.rentApp.service;

import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.model.entity.CarModel;
import by.academy.rentApp.model.entity.User;
import by.academy.rentApp.model.repository.CarModelRepository;
import by.academy.rentApp.model.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testFindUserById() {

        User user = new User();
        user.setId(1);
        user.setUserName("Admin");
        given(this.userRepository.findUserById(any()))
                .willReturn(user);
        UserFormDto userFormDto = userService.findUserById(1);
        assertThat(userFormDto).isNotNull();
        assertThat(userFormDto.getId()).isEqualTo(1);
        assertThat(userFormDto.getUserName()).isEqualTo("Admin");

    }

    @Test
    public void testGetAll() {

        List<User> users = new ArrayList<>();
        users.add(new User());
        given(this.userRepository.findAll())
                .willReturn(users);
        List<UserDto> userDtos = userService.getAll(null);
        assertThat(userDtos.size()).isEqualTo(1);

    }

    @Test
    public void testExistById() {

        given(this.userRepository.existsById(any())).willReturn(true);
        assertThat(userRepository.existsById(10)).isTrue();

    }
//
    @Test
    public void testSaveModel() {

        User user = new User();
        user.setId(1);
        user.setUserName("Admin");
        when(userRepository.save(any(User.class)))
                .thenReturn(user);
        UserFormDto newUserFormDto = new UserFormDto();
        newUserFormDto.setId(1);
        newUserFormDto.setUserName("Admin");
        UserFormDto savedUserFormDto = userService.saveUser(newUserFormDto,false);
        assertThat(savedUserFormDto.getUserName()).isSameAs(newUserFormDto.getUserName());

    }
}

