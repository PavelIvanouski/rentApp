package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.mapper.BrandMapper;
import by.academy.rentApp.mapper.EngineMapper;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.model.repository.BrandRepository;
import by.academy.rentApp.model.repository.EngineRepository;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.EngineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDto> getAll() {
        List<Brand> brands = brandRepository.findAll();
        List<BrandDto> brandDtos = new ArrayList<>();
        brands.forEach(brand -> {
            brandDtos.add(BrandMapper.INSTANCE.brandToBrandDto(brand));
        });
        return brandDtos;
    }

    @Override
    @Transactional
    public BrandDto saveBrand(BrandDto brandDto) {
        Brand savedBrand = brandRepository.save(BrandMapper.INSTANCE.brandDtoToBrand(brandDto));
        return BrandMapper.INSTANCE.brandToBrandDto(savedBrand);
    }

    @Override
    public BrandDto findBrandById(Integer id) {
        Brand brand = brandRepository.findBrandById(id);
        return BrandMapper.INSTANCE.brandToBrandDto(brand);
    }

    @Override
    public void deleteBrand(BrandDto brandDto) {
        brandRepository.delete(BrandMapper.INSTANCE.brandDtoToBrand(brandDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return brandRepository.existsById(id);
    }

}
