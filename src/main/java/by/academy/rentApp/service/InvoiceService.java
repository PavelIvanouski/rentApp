package by.academy.rentApp.service;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.dto.OrderDto;

import java.util.List;

public interface InvoiceService {
    List<InvoiсeDto> getAll();

    List<InvoiсeDto> getAllByOrder(OrderDto orderDto);

    InvoiсeDto saveInvoice(InvoiсeDto invoiсeDto);

    InvoiсeDto findInvoiceById(Integer id);

    void deleteInvoice(InvoiсeDto invoiсeDto);

    boolean existsById(Integer id);

}
