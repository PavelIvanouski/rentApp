package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.StatusDto;
import by.academy.rentApp.model.entity.Status;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", config = CommonMapperConfig.class)
public interface StatusMapper {

    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);

    StatusDto statusToStatusDto(Status status);

    Status statusDtoToStatus(StatusDto statusDto);
}
