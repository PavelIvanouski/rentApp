package by.academy.rentApp.mapper;


import by.academy.rentApp.dto.StatusDto;
import by.academy.rentApp.dto.TypeDto;
import by.academy.rentApp.model.entity.Status;
import by.academy.rentApp.model.entity.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StatusMapperImpl.class})
public class StatusMapperTest {

    @Autowired
    private StatusMapper statusMapper;

    @Test
    public void testStatusToStatusDto() {

        Status status = new Status();
        status.setId(1);
        StatusDto statusDto = statusMapper.statusToStatusDto(status);
        assertThat(statusDto).isNotNull();
        assertThat(statusDto).hasFieldOrPropertyWithValue("id", 1);

    }

    @Test
    public void testStatusDtoToStatus() {

        StatusDto statusDto = new StatusDto();
        statusDto.setName("invoiced");
        Status status = statusMapper.statusDtoToStatus(statusDto);
        assertThat(status).isNotNull();
        assertThat(status).hasFieldOrPropertyWithValue("name", "invoiced");

    }

}
