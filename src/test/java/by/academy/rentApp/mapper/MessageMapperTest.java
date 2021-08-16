package by.academy.rentApp.mapper;


import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.dto.MessagesDto;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.model.entity.Messages;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MessagesMapperImpl.class})
public class MessageMapperTest {

    @Autowired
    private MessagesMapper messagesMapper;

    @Test
    public void testMessagesToMessagesDto() {

        Messages messages = new Messages();
        messages.setId(100);
        MessagesDto messagesDto = messagesMapper.messagesToMessagesDto(messages);
        assertThat(messagesDto).isNotNull();
        assertThat(messagesDto).hasFieldOrPropertyWithValue("id", 100);

    }

    @Test
    public void testMessagesDtoToMessages() {

        MessagesDto messagesDto = new MessagesDto();
        messagesDto.setId(100);
        Messages messages = messagesMapper.messagesDtoToMessages(messagesDto);
        assertThat(messages).isNotNull();
        assertThat(messages).hasFieldOrPropertyWithValue("id", 100);

    }

}
