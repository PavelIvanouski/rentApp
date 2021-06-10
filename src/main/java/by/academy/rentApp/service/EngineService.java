package by.academy.rentApp.service;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.model.entity.Engine;

import java.util.List;

public interface EngineService {
    List<EngineDto> getAll();
}
