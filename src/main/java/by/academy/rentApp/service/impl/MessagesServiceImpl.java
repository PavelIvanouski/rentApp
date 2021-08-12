package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.MessagesDto;
import by.academy.rentApp.mapper.BrandMapper;
import by.academy.rentApp.mapper.MessagesMapper;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Messages;
import by.academy.rentApp.model.repository.BrandRepository;
import by.academy.rentApp.model.repository.MessagesRepository;
import by.academy.rentApp.service.MessagesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;

    private final MessagesMapper messagesMapper;

    public MessagesServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper, MessagesRepository messagesRepository, MessagesMapper messagesMapper) {
        this.messagesRepository = messagesRepository;
        this.messagesMapper = messagesMapper;
    }

    @Override
    public List<MessagesDto> getAll() {
        List<Messages> messages = messagesRepository.findAll();
        List<MessagesDto> messagesDtos = new ArrayList<>();
        messages.forEach(message -> {
            messagesDtos.add(messagesMapper.messagesToMessagesDto(message));
        });
        return messagesDtos;
    }


    @Override
    public MessagesDto findMessageById(Integer id) {
        Messages messages = messagesRepository.findMessagesById(id);
        return messagesMapper.messagesToMessagesDto(messages);
    }

    @Override
    @Transactional
    public MessagesDto saveMessage(MessagesDto messagesDto) {
        Messages savedMessage = messagesRepository.save(messagesMapper.messagesDtoToMessages(messagesDto));
        return messagesMapper.messagesToMessagesDto(savedMessage);
    }


    @Override
    public boolean existsById(Integer id) {
        return messagesRepository.existsById(id);
    }

}
