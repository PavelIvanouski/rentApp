package by.academy.rentApp.dto;

import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Status;
import by.academy.rentApp.model.entity.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class OrderDto {

    private Integer id;

    @NotNull(message = "Rent begin should not be empty")
    private Timestamp rentBegin;

    @NotNull(message = "Rent end should not be empty")
    private Timestamp rentEnd;

    private Timestamp creatingDate;

    private Timestamp updatingDate;

    private CarDto car;

    private User user;

    private Status status;

    private Integer price;

    private Integer total;

}
