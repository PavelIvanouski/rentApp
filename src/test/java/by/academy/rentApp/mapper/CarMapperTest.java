package by.academy.rentApp.mapper;


import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.model.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CarMapperImpl.class, CarModelMapperImpl.class
        , BrandMapperImpl.class, EngineMapperImpl.class, TypeMapperImpl.class})
public class CarMapperTest {

    @Autowired
    private CarMapper carMapper;

    @Test
    public void testCarToCarDto() {

        Car car = new Car();
        car.setId(1);
        CarModel carModel = new CarModel(1, "Polo"
                , new Brand(1, "VW", null), null, null, null);
        car.setModel(carModel);
        car.setType(new Type(1, "sedan", null));
        car.setEngine(new Engine(1, "Diesel", null));
        CarDto carDto = carMapper.carToCarDto(car);
        assertThat(carDto).isNotNull();
        assertThat(carDto).hasFieldOrPropertyWithValue("id", 1);

    }

    @Test
    public void testCarDtoToCar() {

        CarModelDto carModelDto = new CarModelDto();
        CarDto carDto = new CarDto();
        carDto.setAutoTransmission(true);
        Car car = carMapper.carDtoToCar(carDto);
        assertThat(car).isNotNull();
        assertThat(car).hasFieldOrPropertyWithValue("autoTransmission",true);

    }

}
