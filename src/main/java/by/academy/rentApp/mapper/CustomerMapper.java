package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.CustomerDto;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);

}
