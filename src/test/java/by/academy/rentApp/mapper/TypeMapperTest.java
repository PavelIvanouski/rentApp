package by.academy.rentApp.mapper;


import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.dto.TypeDto;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.model.entity.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TypeMapperImpl.class})
public class TypeMapperTest {

    @Autowired
    private TypeMapper typeMapper;

    @Test
    public void testTypeToTypeDto() {

        Type type = new Type();
        type.setId(1);
        TypeDto typeDto = typeMapper.typeToTypeDto(type);
        assertThat(typeDto).isNotNull();
        assertThat(typeDto).hasFieldOrPropertyWithValue("id", 1);

    }

    @Test
    public void testTypeDtoToType() {

        TypeDto typeDto = new TypeDto();
        typeDto.setName("sedan");
        Type type = typeMapper.typeDtoToType(typeDto);
        assertThat(type).isNotNull();
        assertThat(type).hasFieldOrPropertyWithValue("name", "sedan");

    }

}
