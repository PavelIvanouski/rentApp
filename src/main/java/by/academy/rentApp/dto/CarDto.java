package by.academy.rentApp.dto;

import by.academy.rentApp.model.entity.CarModel;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.model.entity.Type;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CarDto {

    private int id;

    @NotBlank(message = "VIN should not be empty")
    @Size(min = 17, max = 17, message = "VIN size must be 17")
    private String vin;

    @NotBlank(message = "State num should not be empty")
    @Size(min = 2, max = 10, message = "State num size must be between 2 and 10")
    private String stateNum;

    @NotBlank(message = "Year should not be empty")
    @Size(min = 4, max = 4, message = "Year size must be 4")
    private String year;

    private int engineVolume;

    @Min(value = 2, message = "Seats num should not be less than 2")
    @Max(value = 9, message = "Seats num should not be greater than 9")
    private int seatsNum;

    private boolean autoTransmission;

    @NotNull(message = "Model should not be empty")
    private CarModel model;

    @NotNull(message = "Engine should not be empty")
    private Engine engine;

    @NotNull(message = "Type should not be empty")
    private Type type;

}
