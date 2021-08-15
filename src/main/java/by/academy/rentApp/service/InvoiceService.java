package by.academy.rentApp.service;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.model.entity.User;

import java.util.List;

public interface InvoiceService {
    List<InvoiсeDto> getAll();

    List<InvoiсeDto> getAllByOrder(OrderDto orderDto);

    List<InvoiсeDto> getAllByUser(UserDto userDto);

    InvoiсeDto saveInvoice(InvoiсeDto invoiсeDto);

    InvoiсeDto findInvoiceById(Integer id);

    void deleteInvoice(InvoiсeDto invoiсeDto);

    boolean existsById(Integer id);

}
