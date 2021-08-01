package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.dto.TypeDto;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.model.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = CommonMapperConfig.class)
public interface TypeMapper {

    TypeMapper INSTANCE = Mappers.getMapper(TypeMapper.class);

    TypeDto typeToTypeDto(Type type);

    Type typeDtoToType(TypeDto typeDto);
}
