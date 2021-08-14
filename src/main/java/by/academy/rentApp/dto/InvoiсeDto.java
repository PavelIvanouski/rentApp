package by.academy.rentApp.dto;

import by.academy.rentApp.model.entity.Order;
import by.academy.rentApp.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;


@Data
public class InvoiсeDto {

    private Integer id;

    private Integer serialNumber;

    private OffsetDateTime creatingDate;

    private String message;

    @Size(min = 1, message = "Total should not be 0")
    private double total;

//    @NotNull(message = "User should not be empty")
//    private User user;

    private OrderDto order;
}
