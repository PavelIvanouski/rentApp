package by.academy.rentApp.service;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.exception.ValidationException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface EngineService {
    List<EngineDto> getAll();

    EngineDto saveEngine(EngineDto engineDto) throws ValidationException;

    EngineDto findEngineById(Integer id);

    void deleteEngine(EngineDto engineDto);

    boolean existsById(Integer id);
//    EngineIdDto getEngineById(Integer id);
//    EngineNameDto getEngineByName(String name);

}
