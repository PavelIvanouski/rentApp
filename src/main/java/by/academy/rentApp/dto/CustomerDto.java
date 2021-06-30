package by.academy.rentApp.dto;

import by.academy.rentApp.model.entity.Rent;
import by.academy.rentApp.model.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CustomerDto {

    private int id;

    @NotBlank(message = "Login should not be empty")
    @Size(min = 2, max = 30, message = "Login size must be between 2 and 30")
    private String login;

    @NotBlank(message = "Password should not be empty")
    @Size(min = 2, max = 20, message = "Password size must be between 2 and 20")
    private String password;

    @NotBlank(message = "First name should not be empty")
    @Size(min = 2, max = 40, message = "First name size must be between 2 and 40")
    private String firstName;

    @NotBlank(message = "Last name should not be empty")
    @Size(min = 2, max = 40, message = "Last name size must be between 2 and 40")
    private String lastName;

    @NotBlank(message = "Passpord should not be empty")
    @Size(min = 14, max = 14, message = "Passpord size must be 14")
    private String passport;

    @NotBlank(message = "Email should not be empty")
    @Size(min = 2, max = 40, message = "Email size must be between 2 and 40")
    private String email;

    private boolean active;

    private Set<Role> roles =new HashSet<>(0);

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Set<Role> roles =new HashSet<>(0);
//
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private List<Rent> rentList;
}
