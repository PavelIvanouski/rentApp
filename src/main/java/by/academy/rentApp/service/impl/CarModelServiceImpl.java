package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.mapper.CarModelMapper;
import by.academy.rentApp.model.entity.CarModel;
import by.academy.rentApp.model.repository.CarModelRepository;
import by.academy.rentApp.service.CarModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarModelServiceImpl implements CarModelService {
    private final CarModelRepository carModelRepository;

    private  final CarModelMapper carModelMapper;

    public CarModelServiceImpl(CarModelRepository carModelRepository, CarModelMapper carModelMapper) {
        this.carModelRepository = carModelRepository;
        this.carModelMapper = carModelMapper;
    }

    @Override
    public List<CarModelDto> getAll() {
        List<CarModel> carModels = carModelRepository.findAll();
        List<CarModelDto> carModelDtos = new ArrayList<>();
        carModels.forEach(carModel -> {
            carModelDtos.add(carModelMapper.carModelToCarModelDto(carModel));
        });
        return carModelDtos;
    }

    @Override
    @Transactional
    public CarModelDto saveModel(CarModelDto carModelDto) {

        if (carModelDto.getId() == null) {
            carModelDto.setCreatingDate(OffsetDateTime.now());
        } else {
            carModelDto.setUpdatingDate(OffsetDateTime.now());
        }

        CarModel savedCarModel = carModelRepository.save(carModelMapper.carModelDtoToCarModel(carModelDto));
        return carModelMapper.carModelToCarModelDto(savedCarModel);
    }

    @Override
    public CarModelDto findModelById(Integer id) {
        CarModel carModel = carModelRepository.findCarModelById(id);
        return carModelMapper.carModelToCarModelDto(carModel);
    }

    @Override
    public CarModelDto findModelByName(String name) {
        CarModel carModel = carModelRepository.findCarModelByName(name);
        return carModelMapper.carModelToCarModelDto(carModel);
    }

    @Override
    public void deleteModel(CarModelDto carModelDto) {
        carModelRepository.delete(carModelMapper.carModelDtoToCarModel(carModelDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return carModelRepository.existsById(id);
    }

}
