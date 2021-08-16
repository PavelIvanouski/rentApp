package by.academy.rentApp.service;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.model.repository.BrandRepository;
import by.academy.rentApp.model.repository.EngineRepository;
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
public class EngineServiceTest {

    @MockBean
    private EngineRepository engineRepository;

    @Autowired
    private EngineService engineService;

    @Test
    public void testFindEngineById() {

        given(this.engineRepository.findEngineById(any()))
                .willReturn(new Engine(35, "Diesel", null));
        EngineDto engineDto = engineService.findEngineById(35);
        assertThat(engineDto).isNotNull();
        assertThat(engineDto.getId()).isEqualTo(35);
        assertThat(engineDto.getName()).isEqualTo("Diesel");

    }

    @Test
    public void testGetAll() {

        List<Engine> engines = new ArrayList<>();
        engines.add(new Engine());
        engines.add(new Engine());
        engines.add(new Engine());

        given(this.engineRepository.findAll())
                .willReturn(engines);
        List<EngineDto> engineDtos = engineService.getAll();
        assertThat(engineDtos.size()).isEqualTo(3);

    }

    @Test
    public void testExistById() {

        given(this.engineRepository.existsById(any())).willReturn(true);
        assertThat(engineService.existsById(10)).isTrue();

    }

    @Test
    public void testSaveEngine() {

        Engine engine = new Engine();
        engine.setId(1);
        engine.setName("Diesel");
        when(engineRepository.save(any(Engine.class)))
                .thenReturn(engine);
        EngineDto newEngineDto = new EngineDto(1,"Diesel");
        EngineDto savedEngine = engineService.saveEngine(newEngineDto);
        assertThat(savedEngine.getName()).isSameAs(newEngineDto.getName());

    }
}

