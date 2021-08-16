package by.academy.rentApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineDto {
    private Integer id;
    @NotBlank(message = "{label.notEmptyName}")
    @Size(min = 2, max = 20, message = "{label.nameSize} [2;20]")
    private String name;
}
