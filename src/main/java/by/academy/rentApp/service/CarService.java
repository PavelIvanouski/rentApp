package by.academy.rentApp.service;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.CarDto;

import java.util.List;

public interface CarService {
    List<CarDto> getAll(String keyword);

//    List<CarDto> search(String keyword);

    CarDto saveCar(CarDto carDto);

    CarDto findCarById(Integer id);

    void deleteCar(CarDto carDto);

    boolean existsById(Integer id);

}
