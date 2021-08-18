package by.academy.rentApp.service;

import by.academy.rentApp.dto.*;
import by.academy.rentApp.model.entity.User;

import java.util.List;

public interface InvoiceService {
    List<InvoiсeDto> getAll(String keyword);

    List<InvoiсeDto> getAllByOrder(OrderDto orderDto);

    List<InvoiсeDto> getAllByUser(UserFormDto userFormDto, String keyword);

    InvoiсeDto saveInvoice(InvoiсeDto invoiсeDto);

    InvoiсeDto findInvoiceById(Integer id);

    void deleteInvoice(InvoiсeDto invoiсeDto);

    boolean existsById(Integer id);

}
