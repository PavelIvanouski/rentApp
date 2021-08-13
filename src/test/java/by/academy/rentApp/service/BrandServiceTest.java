package by.academy.rentApp.service;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.repository.BrandRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandServiceTest {

    @MockBean
    private BrandRepository brandRepository;

    @Autowired
    private BrandService brandService;
//
//    @Autowired
//    private BrandMapper brandMapper;

    @Test
    public void testFindBrandById() {

        given(this.brandRepository.findBrandById(any()))
                .willReturn(new Brand(35, "Ford", null));
        BrandDto brandDto = brandService.findBrandById(35);
        assertThat(brandDto).isNotNull();
        assertThat(brandDto.getId()).isEqualTo(35);
        assertThat(brandDto.getName()).isEqualTo("Ford");

    }

    @Test
    public void testGetAll() {

        given(this.brandRepository.findAll())
                .willReturn(List.of(new Brand(35, "Ford", null)
                        , new Brand(36, "Mers", null)));
        List<BrandDto> brandDtos = brandService.getAll();
        assertThat(brandDtos.size()).isEqualTo(2);

    }

    @Test
    public void testExistById() {

        given(this.brandRepository.existsById(any())).willReturn(true);
        assertThat(brandService.existsById(10)).isTrue();

    }

    @Test
    public void testSaveBrand() {
        //when_save_brand_it_should_return_brand

        when(brandRepository.save(any(Brand.class)))
                .thenReturn(new Brand(35, "Ford", null));
        BrandDto newBrand = new BrandDto(35, "Ford");
        BrandDto savedBrand = brandService.saveBrand(newBrand);
        assertThat(savedBrand.getName()).isSameAs(newBrand.getName());

    }
}

