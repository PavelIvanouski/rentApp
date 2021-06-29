package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Engine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper {

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    BrandDto brandToBrandDto(Brand brand);

    Brand brandDtoToBrand(BrandDto brandDto);

}
