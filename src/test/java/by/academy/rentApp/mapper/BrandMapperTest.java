package by.academy.rentApp.mapper;


import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.model.entity.Brand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
//@SpringBootTest
@SpringBootTest(classes = {BrandMapperImpl.class})
public class BrandMapperTest {

    @Autowired
//    private BrandMapperImpl brandMapper;
    private BrandMapper brandMapper;

    @Test
    public void testBrandToBrandDto() {

        Brand brand = new Brand();
        brand.setId(100);
        BrandDto brandDto = brandMapper.brandToBrandDto(brand);
        assertThat(brandDto).isNotNull();
        assertThat(brandDto).hasFieldOrPropertyWithValue("id", 100);

    }

    @Test
    public void testBrandDtoToBrand() {

        BrandDto brandDto = new BrandDto();
        brandDto.setName("Opel");
        Brand brand = brandMapper.brandDtoToBrand(brandDto);
        assertThat(brand).isNotNull();
        assertThat(brand).hasFieldOrPropertyWithValue("name", "Opel");

    }

}
