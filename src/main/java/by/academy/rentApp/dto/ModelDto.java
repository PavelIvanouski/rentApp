package by.academy.rentApp.dto;

import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.model.entity.Type;
import lombok.Data;

@Data
public class ModelDto {
    private Integer id;
    private String name;
    private int seatsNum;
    private int engineVolume;
    private String  engine;
    private String  type;
    private boolean autoTransmission;
}
