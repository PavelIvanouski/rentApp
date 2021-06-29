package by.academy.rentApp.service;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.CarModelDto;

import java.util.List;

public interface CarModelService {
    List<CarModelDto> getAll();

    CarModelDto saveModel(CarModelDto carModelDto);

    CarModelDto findModelById(Integer id);

    void deleteModel(CarModelDto carModelDto);

    boolean existsById(Integer id);

}
