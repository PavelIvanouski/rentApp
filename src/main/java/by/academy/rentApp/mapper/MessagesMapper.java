package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.MessagesDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Messages;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", config = CommonMapperConfig.class)
public interface MessagesMapper {

    MessagesMapper INSTANCE = Mappers.getMapper(MessagesMapper.class);

    MessagesDto messagesToMessagesDto(Messages messages);

    Messages messagesDtoToMessages(MessagesDto messagesDto);

}
