package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.CarModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = CommonMapperConfig.class,uses = {BrandMapper.class})
//@Mapper
public interface CarModelMapper {

    CarModelMapper INSTANCE = Mappers.getMapper(CarModelMapper.class);

    CarModelDto carModelToCarModelDto(CarModel carModel);

    CarModel carModelDtoToCarModel(CarModelDto carModelDto);

//    Brand BrandDtoToBrand(BrandDto brand);
//
//    BrandDto BrandToBrandDto(Brand brand);
}
