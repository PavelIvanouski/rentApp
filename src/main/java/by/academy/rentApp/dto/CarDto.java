package by.academy.rentApp.dto;

import by.academy.rentApp.model.entity.CarModel;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.model.entity.Type;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Data
public class CarDto {



    @NotBlank(message = "VIN {label.shouldNotBeEmpty}")
    @Size(min = 17, max = 17, message = "VIN {label.mustBe} 17")
    private String vin;

    private Integer id;

    private String stateNum;

    @NotBlank(message = "{label.year} {label.shouldNotBeEmpty}")
    @Size(min = 4, max = 4, message = "{label.year} {label.mustBe} 4")
    private String year;

    private int engineVolume;

    @Min(value = 2, message = "{label.seatsNumber} > 1")
    @Max(value = 9, message = "{label.seatsNumber} < 10")
    private int seatsNum;

    private boolean autoTransmission;

    @NotNull(message = "{label.model1} {label.shouldNotBeEmpty}")
    private CarModelDto model;

    @NotNull(message = "{label.engine1} {label.shouldNotBeEmpty}")
    private EngineDto engine;

    @NotNull(message = "{label.type1} {label.shouldNotBeEmpty}")
    private TypeDto type;

    private OffsetDateTime creatingDate;

    private OffsetDateTime updatingDate;

    private String color;

    private String photos;

    @Min(value = 1, message = "{label.pricePerDay} > 0")
    private Integer price;

    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;

        return "/car-photos/" + id + "/" + photos;
    }

}
