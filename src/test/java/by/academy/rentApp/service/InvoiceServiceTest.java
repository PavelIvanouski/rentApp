package by.academy.rentApp.service;

import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.model.entity.Invoiсe;
import by.academy.rentApp.model.entity.User;
import by.academy.rentApp.model.repository.InvoiceRepository;
import by.academy.rentApp.model.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceServiceTest {

    @MockBean
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Test
    public void testFindInvoiceById() {

        Invoiсe invoiсe = new Invoiсe();
        invoiсe.setId(1);
        invoiсe.setTotal(Double.valueOf("10.45"));
        given(this.invoiceRepository.findInvoiсeById(any()))
                .willReturn(invoiсe);
        InvoiсeDto invoiсeDto = invoiceService.findInvoiceById(1);
        assertThat(invoiсeDto).isNotNull();
        assertThat(invoiсeDto.getId()).isEqualTo(1);
        assertThat(invoiсeDto.getTotal()).isEqualTo(Double.valueOf("10.45"));

    }

//    @Test
//    public void testGetAll() {
//
//        List<Invoiсe> invoiсeList = new ArrayList<>();
//        invoiсeList.add(new Invoiсe());
//        invoiсeList.add(new Invoiсe());
//        invoiсeList.add(new Invoiсe());
//        invoiсeList.add(new Invoiсe());
//        invoiсeList.add(new Invoiсe());
//        given(this.invoiceRepository.findAll())
//                .willReturn(invoiсeList);
//        List<InvoiсeDto> invoiсeDtos = invoiceService.getAll();
//        assertThat(invoiсeDtos.size()).isEqualTo(5);
//
//    }

    @Test
    public void testExistById() {

        given(this.invoiceRepository.existsById(any())).willReturn(true);
        assertThat(invoiceRepository.existsById(10)).isTrue();

    }
//
    @Test
    public void testSaveInvoice() {

        Invoiсe invoiсe = new Invoiсe();
        invoiсe.setId(1);
        invoiсe.setSerialNumber(1);
        when(invoiceRepository.save(any(Invoiсe.class)))
                .thenReturn(invoiсe);
        InvoiсeDto newInvoiсeDto = new InvoiсeDto();
        newInvoiсeDto.setId(1);
        newInvoiсeDto.setSerialNumber(1);
        InvoiсeDto savedInvoiсeDto = invoiceService.saveInvoice(newInvoiсeDto);
        assertThat(savedInvoiсeDto.getSerialNumber()).isSameAs(newInvoiсeDto.getSerialNumber());

    }
}

