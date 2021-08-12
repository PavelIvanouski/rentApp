package by.academy.rentApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Data
public class MessagesDto {

    private Integer id;

    @Size(min = 5,max = 40)
    private String userName;

    @Size(min = 5,max = 40)
    private String email;

    @Size(min = 5,max = 70)
    private String message;

}
