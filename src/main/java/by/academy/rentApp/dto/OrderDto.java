package by.academy.rentApp.dto;

import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Status;
import by.academy.rentApp.model.entity.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Data
public class OrderDto {

    private Integer id;

//    @NotNull(message = "Rent begin should not be empty")
//@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime rentBegin;

//   @NotNull(message = "Rent end should not be empty")
    private OffsetDateTime rentEnd;

    private OffsetDateTime creatingDate;

    private OffsetDateTime updatingDate;

    @NotNull(message = "Car should not be empty")
    private CarDto car;

    @NotNull(message = "User should not be empty")
    private UserFormDto user;

    private StatusDto status;

    private Integer price;


    private double total;

    @Max(value = 70, message = "Max value = 70")
    private String message;

}
