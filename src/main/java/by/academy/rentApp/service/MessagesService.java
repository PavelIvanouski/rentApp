package by.academy.rentApp.service;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.MessagesDto;

import java.util.List;

public interface MessagesService {

    List<MessagesDto> getAll();

    MessagesDto findMessageById(Integer id);

    MessagesDto saveMessage(MessagesDto messagesDto);

    boolean existsById(Integer id);

}
