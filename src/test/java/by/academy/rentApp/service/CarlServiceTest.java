package by.academy.rentApp.service;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.CarModel;
import by.academy.rentApp.model.repository.CarModelRepository;
import by.academy.rentApp.model.repository.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarlServiceTest {

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

    @Test
    public void testFindCarById() {

        Car car = new Car();
        car.setId(1);
        car.setColor("white");
        given(this.carRepository.findCarById(any()))
                .willReturn(car);
        CarDto carDto = carService.findCarById(1);
        assertThat(carDto).isNotNull();
        assertThat(carDto.getId()).isEqualTo(1);
        assertThat(carDto.getColor()).isEqualTo("white");

    }

//    @Test
//    public void testGetAll() {
//
//        List<Car> cars = new ArrayList<>();
//        cars.add(new Car());
//        cars.add(new Car());
//        cars.add(new Car());
//        cars.add(new Car());
//        given(this.carRepository.search(1,1,1))
//                .willReturn(cars);
//        List<CarDto> carDtos = carService.getAll(1,1,1);
//        assertThat(carDtos.size()).isEqualTo(4);
//
//    }

    @Test
    public void testExistById() {

        given(this.carRepository.existsById(any())).willReturn(true);
        assertThat(carRepository.existsById(10)).isTrue();

    }
//
    @Test
    public void testSaveCar() {

        Car car = new Car();
        car.setId(1);
        when(carRepository.save(any(Car.class)))
                .thenReturn(car);
        CarDto newCarDto = new CarDto();
        newCarDto.setId(1);
        CarDto savedCarDto = carService.saveCar(newCarDto);
        assertThat(savedCarDto.getId()).isSameAs(newCarDto.getId());

    }
}

