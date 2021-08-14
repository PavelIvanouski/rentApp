package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.mapper.CarMapper;
import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.repository.CarRepository;
import by.academy.rentApp.service.CarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }


    @Override
    public List<CarDto> getAll(Integer modelId, Integer typeId, Integer engineId) {
        if (modelId != null || typeId != null || engineId != null) {
//            List<Car> cars = carRepository.search(keyword,typeId);
            List<Car> cars = carRepository.search(modelId, typeId, engineId);
            List<CarDto> carDtos = new ArrayList<>();
            cars.forEach(car -> {
                carDtos.add(carMapper.carToCarDto(car));
            });
            return carDtos;
        }
        List<Car> cars = carRepository.findAll();
        List<CarDto> carDtos = new ArrayList<>();
        cars.forEach(car -> {
            carDtos.add(carMapper.carToCarDto(car));
        });
        return carDtos;
    }

//    @Override
//    public List<CarDto> search(String keyword) {
//        List<Car> cars = carRepository.search(keyword);
//        List<CarDto> carDtos = new ArrayList<>();
//        cars.forEach(car -> {
//            carDtos.add(carMapper.carToCarDto(car));
//        });
//        return carDtos;
//    }

    @Override
    @Transactional
    public CarDto saveCar(CarDto carDto) {
        if (carDto.getId() == null) {
            carDto.setCreatingDate(OffsetDateTime.now());
        } else {
            carDto.setUpdatingDate(OffsetDateTime.now());
        }
        Car savedCar = carRepository.save(carMapper.carDtoToCar(carDto));
        return carMapper.carToCarDto(savedCar);
    }

    @Override
    public CarDto findCarById(Integer id) {
        Car car = carRepository.findCarById(id);
        return carMapper.carToCarDto(car);
    }

    @Override
    public void deleteCar(CarDto carDto) {
        carRepository.delete(carMapper.carDtoToCar(carDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return carRepository.existsById(id);
    }
}
