package by.academy.rentApp.dto;

import by.academy.rentApp.model.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.OffsetDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarModelDto {

    private Integer id;

    @NotBlank(message = "{label.notEmptyName}")
    @Size(min = 2, max = 30, message = "{label.nameSize} [2;20]")
    private String name;

    @NotNull(message = "{label.notEmptyName}")
    private BrandDto brand;

    private OffsetDateTime creatingDate;

    private OffsetDateTime updatingDate;

}
