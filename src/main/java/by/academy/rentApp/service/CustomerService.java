package by.academy.rentApp.service;

import by.academy.rentApp.dto.CustomerDto;
import by.academy.rentApp.dto.EngineDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAll();

    CustomerDto saveCustomer(CustomerDto customerDto);

    CustomerDto findCustomerById(Integer id);

    CustomerDto findCustomerByEmail(String email);

    void deleteCustomer(CustomerDto customerDto);

    boolean existsById(Integer id);

}
