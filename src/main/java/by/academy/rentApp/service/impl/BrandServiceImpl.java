package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.mapper.BrandMapper;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.repository.BrandRepository;
import by.academy.rentApp.service.BrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public List<BrandDto> getAll() {
        List<Brand> brands = brandRepository.findAll();
        List<BrandDto> brandDtos = new ArrayList<>();
        brands.forEach(brand -> {
            brandDtos.add(brandMapper.brandToBrandDto(brand));
        });
        return brandDtos;
    }

    @Override
    @Transactional
    public BrandDto saveBrand(BrandDto brandDto) {
        Brand savedBrand = brandRepository.save(brandMapper.brandDtoToBrand(brandDto));
        return brandMapper.brandToBrandDto(savedBrand);
    }

    @Override
    public BrandDto findBrandById(Integer id) {
        Brand brand = brandRepository.findBrandById(id);
        return brandMapper.brandToBrandDto(brand);
    }

    @Override
    public BrandDto findBrandByName(String name) {
        Brand brand = brandRepository.findBrandByName(name);
        return brandMapper.brandToBrandDto(brand);
    }

    @Override
    public void deleteBrand(BrandDto brandDto) {
        brandRepository.delete(brandMapper.brandDtoToBrand(brandDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return brandRepository.existsById(id);
    }

}
