package by.academy.rentApp.dto;

import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Status;
import by.academy.rentApp.model.entity.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Data
public class OrderDto {

    private Integer id;

//    @NotNull(message = "Rent begin should not be empty")
    private OffsetDateTime rentBegin;

//    @NotNull(message = "Rent end should not be empty")
    private OffsetDateTime rentEnd;

    private OffsetDateTime creatingDate;

    private OffsetDateTime updatingDate;

    private CarDto car;

    private User user;

    private Status status;

    private Integer price;

    private Integer total;

}
