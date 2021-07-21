package by.academy.rentApp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BrandDto {

    @NotNull(message = "Brand should not be empty")
    private Integer id;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name size must be between 2 and 30")
    private String name;
}
