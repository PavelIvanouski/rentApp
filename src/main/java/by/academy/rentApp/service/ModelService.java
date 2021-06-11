package by.academy.rentApp.service;

import by.academy.rentApp.dto.ModelDto;

import java.util.List;

public interface ModelService {
    List<ModelDto> getAll();
    ModelDto getModel(Integer id);
}
