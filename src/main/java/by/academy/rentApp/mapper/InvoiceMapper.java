package by.academy.rentApp.mapper;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.model.entity.Car;
import by.academy.rentApp.model.entity.Invoiсe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", config = CommonMapperConfig.class
        ,uses = {OrderMapper.class})
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    InvoiсeDto invoiceToInvoiceDto(Invoiсe invoiсe);

    Invoiсe invoiceDtoToInvoice(InvoiсeDto invoiсeDto);

}
