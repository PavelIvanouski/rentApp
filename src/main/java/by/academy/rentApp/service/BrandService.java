package by.academy.rentApp.service;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.EngineDto;

import java.util.List;

public interface BrandService {
    List<BrandDto> getAll();

    BrandDto saveBrand(BrandDto brandDto);

    BrandDto findBrandById(Integer id);

    BrandDto findBrandByName(String name);

    void deleteBrand(BrandDto brandDto);

    boolean existsById(Integer id);

}
