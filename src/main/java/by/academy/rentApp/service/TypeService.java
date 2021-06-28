package by.academy.rentApp.service;

import by.academy.rentApp.dto.TypeDto;

import java.util.List;

public interface TypeService {
    List<TypeDto> getAll();

    TypeDto saveType(TypeDto typeDto);

    TypeDto findTypeById(Integer id);

    void deleteType(TypeDto typeDto);

    boolean existsById(Integer id);
}
