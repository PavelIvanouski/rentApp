package by.academy.rentApp.service;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.TypeDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Type;
import by.academy.rentApp.model.repository.BrandRepository;
import by.academy.rentApp.model.repository.TypeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeServiceTest {

    @MockBean
    private TypeRepository typeRepository;

    @Autowired
    private TypeService typeService;

    @Test
    public void testFindTypeById() {

        given(this.typeRepository.findTypeById(any()))
                .willReturn(new Type(1, "Sedan",null ));
        TypeDto typeDto = typeService.findTypeById(1);
        assertThat(typeDto).isNotNull();
        assertThat(typeDto.getId()).isEqualTo(1);
        assertThat(typeDto.getName()).isEqualTo("Sedan");

    }

    @Test
    public void testGetAll() {

        given(this.typeRepository.findAll())
                .willReturn(List.of(new Type()
                        , new Type()));
        List<TypeDto> typeDtos = typeService.getAll();
        assertThat(typeDtos.size()).isEqualTo(2);

    }

    @Test
    public void testExistById() {

        given(this.typeRepository.existsById(any())).willReturn(true);
        assertThat(typeRepository.existsById(10)).isTrue();

    }

    @Test
    public void testSaveType() {

        when(typeRepository.save(any(Type.class)))
                .thenReturn(new Type(1,"Sedan",null));
        TypeDto newTypeDto = new TypeDto(1,"Sedan");
        TypeDto savedTypeDto = typeService.saveType(newTypeDto);
        assertThat(savedTypeDto.getName()).isSameAs(savedTypeDto.getName());

    }
}

