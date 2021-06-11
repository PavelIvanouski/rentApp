package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.TypeDto;
import by.academy.rentApp.model.dao.TypeDao;
import by.academy.rentApp.model.entity.Type;
import by.academy.rentApp.service.TypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    private final TypeDao dao;

    public TypeServiceImpl(TypeDao dao) {
        this.dao = dao;
    }

    @Override
    public List<TypeDto> getAll() {
        List<Type> types = dao.findAll();
        List<TypeDto> typeDtos = new ArrayList<>();

        types.forEach(type -> {
            TypeDto typeDto = new TypeDto();
            typeDto.setId(type.getId());
            typeDto.setName(type.getName());
            typeDtos.add(typeDto);
        });

        return typeDtos;
    }
}
