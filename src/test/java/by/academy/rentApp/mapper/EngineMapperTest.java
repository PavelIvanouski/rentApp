package by.academy.rentApp.mapper;


import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Engine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EngineMapperImpl.class})
public class EngineMapperTest {

    @Autowired
    private EngineMapper engineMapper;

    @Test
    public void testEngineToEngineDto() {

        Engine engine = new Engine();
        engine.setId(100);
        EngineDto engineDto = engineMapper.engineToEngineDto(engine);
        assertThat(engineDto).isNotNull();
        assertThat(engineDto).hasFieldOrPropertyWithValue("id", 100);

    }

    @Test
    public void testEngineDtoToEngine() {

        EngineDto engineDto = new EngineDto();
        engineDto.setName("Diesel");
        Engine engine = engineMapper.engineDtoToEngine(engineDto);
        assertThat(engine).isNotNull();
        assertThat(engine).hasFieldOrPropertyWithValue("name", "Diesel");

    }

}
