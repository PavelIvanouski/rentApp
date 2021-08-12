package by.academy.rentApp.serviceTest;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.CarModel;
import by.academy.rentApp.model.repository.BrandRepository;
import by.academy.rentApp.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Array;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BrandServiceTests {

//    @MockBean
//    private BrandRepository brandRepository;
//
//    @Autowired
//    private BrandService brandService;
//
//    @Test
//    public void testFindBrandById() {
//        given(this.brandRepository.getById(any()))
//                .willReturn(new Brand(1,"VW",null));
//        BrandDto brandDto = brandService.findBrandById(1);
//        assertThat(brandDto.getId()).isEqualTo(1);
//
//    }
}
