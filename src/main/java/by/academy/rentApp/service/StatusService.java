package by.academy.rentApp.service;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.StatusDto;

import java.util.List;

public interface StatusService {
    List<StatusDto> getAll();

    List<StatusDto> getAllByIdList(List<Integer> statusIdList);

    StatusDto saveStatus(StatusDto statusDto);

    StatusDto findStatusById(Integer id);

    StatusDto findStatusByName(String name);

    void deleteStatus(StatusDto statusDto);

    boolean existsById(Integer id);

}
