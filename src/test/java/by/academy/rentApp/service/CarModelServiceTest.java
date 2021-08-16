package by.academy.rentApp.service;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.CarModel;
import by.academy.rentApp.model.repository.BrandRepository;
import by.academy.rentApp.model.repository.CarModelRepository;
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
public class CarModelServiceTest {

    @MockBean
    private CarModelRepository carModelRepository;

    @Autowired
    private CarModelService carModelService;

    @Test
    public void testFindBrandById() {

        CarModel carModel = new CarModel();
        carModel.setId(1);
        carModel.setName("Polo");
        given(this.carModelRepository.findCarModelById(any()))
                .willReturn(carModel);
        CarModelDto carModelDto = carModelService.findModelById(1);
        assertThat(carModelDto).isNotNull();
        assertThat(carModelDto.getId()).isEqualTo(1);
        assertThat(carModelDto.getName()).isEqualTo("Polo");

    }

    @Test
    public void testGetAll() {

        List<CarModel> carModels = new ArrayList<>();
        carModels.add(new CarModel());
        carModels.add(new CarModel());

        given(this.carModelRepository.findAll())
                .willReturn(carModels);
        List<CarModelDto> carModelDtos = carModelService.getAll();
        assertThat(carModelDtos.size()).isEqualTo(2);

    }

    @Test
    public void testExistById() {

        given(this.carModelRepository.existsById(any())).willReturn(true);
        assertThat(carModelService.existsById(10)).isTrue();

    }
//
    @Test
    public void testSaveModel() {

        CarModel carModel = new CarModel();
        carModel.setId(1);
        carModel.setName("Polo");
        when(carModelRepository.save(any(CarModel.class)))
                .thenReturn(carModel);
        CarModelDto newCarModelDto = new CarModelDto(1,"Polo",null,null,null);
        CarModelDto savedModel = carModelService.saveModel(newCarModelDto);
        assertThat(savedModel.getName()).isSameAs(newCarModelDto.getName());

    }
}

