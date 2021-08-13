package by.academy.rentApp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TypeDto {
    private Integer id;
    @NotBlank(message = "{label.notEmptyName}")
    @Size(min = 2, max = 20, message = "{label.nameSize} [2;20]")
    private String name;
}
