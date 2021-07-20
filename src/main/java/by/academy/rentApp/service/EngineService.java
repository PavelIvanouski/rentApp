package by.academy.rentApp.service;

import by.academy.rentApp.dto.EngineDto;

import java.util.List;

public interface EngineService {
    List<EngineDto> getAll();

    EngineDto saveEngine(EngineDto engineDto);

    EngineDto findEngineById(Integer id);

    EngineDto findEngineByName(String name);

    void deleteEngine(EngineDto engineDto);

    boolean existsById(Integer id);

}
