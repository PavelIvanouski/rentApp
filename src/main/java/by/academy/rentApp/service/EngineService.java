package by.academy.rentApp.service;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.exception.ValidationException;

import java.util.List;

public interface EngineService {
    List<EngineDto> getAll();

    EngineDto saveEngine(EngineDto engineDto) throws ValidationException;



//    EngineIdDto getEngineById(Integer id);
//    EngineNameDto getEngineByName(String name);

}
