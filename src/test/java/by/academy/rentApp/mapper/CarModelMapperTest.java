package by.academy.rentApp.mapper;


import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.CarModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CarModelMapperImpl.class, BrandMapperImpl.class})
public class CarModelMapperTest {

    @Autowired
    private CarModelMapper carModelMapper;

    @Test
    public void testCarModelToCarModelDto() {

        CarModel carModel = new CarModel();
        carModel.setId(1);
        carModel.setName("Polo");
        carModel.setBrand(new Brand(1, "VW", null));
        CarModelDto carModelDto = carModelMapper.carModelToCarModelDto(carModel);
        assertThat(carModelDto).isNotNull();
        assertThat(carModelDto).hasFieldOrPropertyWithValue("id", 1);

    }

    @Test
    public void testCarModelDtoToCarModel() {

        CarModelDto carModelDto = new CarModelDto();
        carModelDto.setId(1);
        carModelDto.setName("Polo");
        carModelDto.setBrand(new BrandDto(1,"VW"));
        CarModel carModel = carModelMapper.carModelDtoToCarModel(carModelDto);
        assertThat(carModel).isNotNull();
        assertThat(carModel).hasFieldOrPropertyWithValue("id",1);

    }

}
