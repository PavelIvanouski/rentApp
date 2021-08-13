package by.academy.rentApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class MessagesDto {

    private Integer id;

    @NotBlank(message = "{label.shouldNotBeEmpty}")
    @Size(min = 1, max = 40, message = "{label.mustRange} [1;40]")
    private String userName;

    @NotBlank(message = "{label.shouldNotBeEmpty}")
    @Size(min = 5, max = 40, message = "{label.mustRange} [5;40]")
    @Email
    private String email;

    @NotBlank(message = "{label.shouldNotBeEmpty}")
    @Size(min = 5, max = 70, message = "{label.mustRange} [5;70]")
    private String message;

}
