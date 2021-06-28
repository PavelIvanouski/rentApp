package by.academy.rentApp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TypeDto {
    private Integer id;
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 20, message = "Name size must be between 2 and 20")
    private String name;
}
