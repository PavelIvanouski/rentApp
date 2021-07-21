package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.mapper.CarMapper;
import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.repository.CarRepository;
import by.academy.rentApp.service.CarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Override
    public List<CarDto> getAll() {
        List<Car> cars = carRepository.findAll();
        List<CarDto> carDtos = new ArrayList<>();
        cars.forEach(car -> {
            carDtos.add(CarMapper.INSTANCE.carToCarDto(car));
        });
        return carDtos;
    }

    @Override
    @Transactional
    public CarDto saveCar(CarDto carDto) {
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        if (carDto.getId() == null) {
            carDto.setCreatingDate(sqlTimestamp);
        } else {
            carDto.setUpdatingDate(sqlTimestamp);
        }
        Car savedCar = carRepository.save(CarMapper.INSTANCE.carDtoToCar(carDto));
        return CarMapper.INSTANCE.carToCarDto(savedCar);
    }

    @Override
    public CarDto findCarById(Integer id) {
        Car car = carRepository.findCarById(id);
        return CarMapper.INSTANCE.carToCarDto(car);
    }

    @Override
    public void deleteCar(CarDto carDto) {
        carRepository.delete(CarMapper.INSTANCE.carDtoToCar(carDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return carRepository.existsById(id);
    }
}
