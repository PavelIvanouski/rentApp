package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.model.entity.Engine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EngineMapper {

    EngineMapper INSTANCE = Mappers.getMapper(EngineMapper.class);

    EngineDto engineToEngineDto(Engine engine);

    Engine engineDtoToEngine(EngineDto engineDto);

//    Engine engineDtoToEngine(EngineDto engineDto);

}
