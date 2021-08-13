package by.academy.rentApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {


    private Integer id;

    @NotBlank(message = "{label.notEmptyName}")
    @Size(min = 2, max = 30, message = "{label.nameSize} [2;20]")
    private String name;
}
