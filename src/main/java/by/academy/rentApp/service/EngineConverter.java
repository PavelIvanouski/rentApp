package by.academy.rentApp.service;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.model.entity.Engine;
import org.springframework.stereotype.Component;

@Component
public class EngineConverter {

    public Engine fromEngineDtoToEngine(EngineDto engineDto) {
        Engine engine = new Engine();
        engine.setId(engineDto.getId());
        engine.setName(engineDto.getName());
        return engine;
    }

    public EngineDto fromEngineToEngineDto(Engine engine) {
//        return EngineDto.builder()
//                .id(engine.getId())
//                .name(engine.getName())
//                .build();
        EngineDto engineDto = new EngineDto();
        engineDto.setId(engine.getId());
        engineDto.setName(engine.getName());
        return engineDto;
    }
}
