package by.academy.rentApp.dto;

import by.academy.rentApp.model.entity.Order;
import by.academy.rentApp.model.entity.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private Integer id;

    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "*Please provide a user name")
    private String userName;

    private String password;

    @NotEmpty(message = "*Please provide your first name")
    private String firstName;

    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    private String passport;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    private Boolean active;

    private OffsetDateTime creatingDate;

    private OffsetDateTime updatingDate;

    private Set<Role> roles;

//    private List<Order> orderList;

}
