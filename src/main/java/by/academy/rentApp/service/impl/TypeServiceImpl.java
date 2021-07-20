package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.TypeDto;
import by.academy.rentApp.mapper.TypeMapper;
import by.academy.rentApp.model.entity.Type;
import by.academy.rentApp.model.repository.TypeRepository;
import by.academy.rentApp.service.TypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<TypeDto> getAll() {
        List<Type> types = typeRepository.findAll();
        List<TypeDto> typeDtos = new ArrayList<>();
        types.forEach(type -> {
            typeDtos.add(TypeMapper.INSTANCE.typeToTypeDto(type));
        });
        return typeDtos;
    }

    @Override
    @Transactional
    public TypeDto saveType(TypeDto typeDto) {
        Type savedType = typeRepository.save(TypeMapper.INSTANCE.typeDtoToType(typeDto));
        return TypeMapper.INSTANCE.typeToTypeDto(savedType);
    }

    @Override
    public TypeDto findTypeById(Integer id) {
        Type type = typeRepository.findTypeById(id);
        return TypeMapper.INSTANCE.typeToTypeDto(type);
    }

    @Override
    public TypeDto findTypeByName(String name) {
        Type type = typeRepository.findTypeByName(name);
        return TypeMapper.INSTANCE.typeToTypeDto(type);
    }

    @Override
    public void deleteType(TypeDto typeDto) {
        typeRepository.delete(TypeMapper.INSTANCE.typeDtoToType(typeDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return typeRepository.existsById(id);
    }

}
