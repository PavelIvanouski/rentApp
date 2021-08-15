package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.dto.UserDto;
import by.academy.rentApp.dto.UserFormDto;
import by.academy.rentApp.mapper.CarMapper;
import by.academy.rentApp.mapper.InvoiceMapper;
import by.academy.rentApp.mapper.OrderMapper;
import by.academy.rentApp.mapper.UserMapper;
import by.academy.rentApp.model.entity.Invoiсe;
import by.academy.rentApp.model.entity.User;
import by.academy.rentApp.model.repository.CarRepository;
import by.academy.rentApp.model.repository.InvoiceRepository;
import by.academy.rentApp.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    public InvoiceServiceImpl(CarRepository carRepository, CarMapper carMapper, InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, OrderMapper orderMapper, UserMapper userMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }


    @Override
    public List<InvoiсeDto> getAll() {
        List<Invoiсe> invoiсes = invoiceRepository.findAll();
        List<InvoiсeDto> invoiсeDtos = new ArrayList<>();
        invoiсes.forEach(invoiсe -> {
            invoiсeDtos.add(invoiceMapper.invoiceToInvoiceDto(invoiсe));
        });
        return invoiсeDtos;
    }

    @Override
    public List<InvoiсeDto> getAllByOrder(OrderDto orderDto) {
        List<Invoiсe> invoiсes = invoiceRepository.findAllByOrder(orderMapper.orderDtoToOrder(orderDto));
        List<InvoiсeDto> invoiсeDtos = new ArrayList<>();
        invoiсes.forEach(invoiсe -> {
            invoiсeDtos.add(invoiceMapper.invoiceToInvoiceDto(invoiсe));
        });
        return invoiсeDtos;
    }

    @Override
    public List<InvoiсeDto> getAllByUser(UserFormDto userFormDto) {
        User user = userMapper.userFormDtoToUser(userFormDto);
        List<Invoiсe> invoiсes = invoiceRepository.findAllByUser(user);
        List<InvoiсeDto> invoiсeDtos = new ArrayList<>();
        invoiсes.forEach(invoiсe -> {
            invoiсeDtos.add(invoiceMapper.invoiceToInvoiceDto(invoiсe));
        });
        return invoiсeDtos;
    }

    @Override
    @Transactional
    public InvoiсeDto saveInvoice(InvoiсeDto invoiсeDto) {
        if (invoiсeDto.getId() == null) {
            invoiсeDto.setCreatingDate(OffsetDateTime.now());
        }
        Invoiсe savedInvoice = invoiceRepository.save(invoiceMapper.invoiceDtoToInvoice(invoiсeDto));
        return invoiceMapper.invoiceToInvoiceDto(savedInvoice);
    }

    @Override
    public InvoiсeDto findInvoiceById(Integer id) {
        Invoiсe invoiсe = invoiceRepository.findInvoiсeById(id);
        return  invoiceMapper.invoiceToInvoiceDto(invoiсe);
    }

    @Override
    public void deleteInvoice(InvoiсeDto invoiсeDto) {
        invoiceRepository.delete(invoiceMapper.invoiceDtoToInvoice(invoiсeDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return  invoiceRepository.existsById(id);
    }
}
