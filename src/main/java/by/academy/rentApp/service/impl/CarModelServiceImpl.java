package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.mapper.CarModelMapper;
import by.academy.rentApp.model.entity.CarModel;
import by.academy.rentApp.model.repository.CarModelRepository;
import by.academy.rentApp.service.CarModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarModelServiceImpl implements CarModelService {
    private final CarModelRepository carModelRepository;

    public CarModelServiceImpl(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Override
    public List<CarModelDto> getAll() {
        List<CarModel> carModels = carModelRepository.findAll();
        List<CarModelDto> carModelDtos = new ArrayList<>();
        carModels.forEach(carModel -> {
            carModelDtos.add(CarModelMapper.INSTANCE.carModelToCarModelDto(carModel));
        });
        return carModelDtos;
    }

    @Override
    @Transactional
    public CarModelDto saveModel(CarModelDto carModelDto) {
        CarModel savedCarModel = carModelRepository.save(CarModelMapper.INSTANCE.carModelDtoToCarModel(carModelDto));
        return CarModelMapper.INSTANCE.carModelToCarModelDto(savedCarModel);
    }

    @Override
    public CarModelDto findModelById(Integer id) {
        CarModel carModel = carModelRepository.findCarModelById(id);
        return CarModelMapper.INSTANCE.carModelToCarModelDto(carModel);
    }

    @Override
    public void deleteModel(CarModelDto carModelDto) {
        carModelRepository.delete(CarModelMapper.INSTANCE.carModelDtoToCarModel(carModelDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return carModelRepository.existsById(id);
    }

}
