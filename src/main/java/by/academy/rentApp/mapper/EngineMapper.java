package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.model.entity.Engine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = CommonMapperConfig.class)
public interface EngineMapper {

    EngineMapper INSTANCE = Mappers.getMapper(EngineMapper.class);

    EngineDto engineToEngineDto(Engine engine);

    Engine engineDtoToEngine(EngineDto engineDto);

//    Engine engineDtoToEngine(EngineDto engineDto);

}
