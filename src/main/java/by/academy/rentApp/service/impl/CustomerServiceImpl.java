package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.CustomerDto;
import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.mapper.CustomerMapper;
import by.academy.rentApp.mapper.EngineMapper;
import by.academy.rentApp.model.entity.Customer;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.model.repository.CustomerRepository;
import by.academy.rentApp.model.repository.EngineRepository;
import by.academy.rentApp.service.CustomerService;
import by.academy.rentApp.service.EngineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        customers.forEach(customer -> {
            customerDtos.add(CustomerMapper.INSTANCE.customerToCustomerDto(customer));
        });
        return customerDtos;
    }

    @Override
    @Transactional
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer savedCustomer = customerRepository.save(CustomerMapper.INSTANCE.customerDtoToCustomer(customerDto));
        return CustomerMapper.INSTANCE.customerToCustomerDto(savedCustomer);
    }

    @Override
    public CustomerDto findCustomerById(Integer id) {
        Customer customer = customerRepository.findCustomerById(id);
        return CustomerMapper.INSTANCE.customerToCustomerDto(customer);
    }

    @Override
    public CustomerDto findCustomerByEmail(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email);
        return CustomerMapper.INSTANCE.customerToCustomerDto(customer);
    }


    @Override
    public void deleteCustomer(CustomerDto customerDto) {
        customerRepository.delete(CustomerMapper.INSTANCE.customerDtoToCustomer(customerDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return customerRepository.existsById(id);
    }

}
