package by.academy.rentApp.service;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.MessagesDto;

import java.util.List;

public interface MessgesService {

    List<MessagesDto> getAll();

    MessagesDto findMessageById(Integer id);

    boolean existsById(Integer id);

}
