package by.academy.rentApp.controller;

import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.service.*;
import by.academy.rentApp.service.impl.UserServiceImpl;
import by.academy.rentApp.util.DatesUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class InvoiceController {

    @Autowired
    ServletContext servletContext;

    private final InvoiceService invoiceService;
    private final OrderService orderService;
    private final UserServiceImpl userService;
    private final TemplateEngine templateEngine;

    public InvoiceController(CarService carService, CarModelService carModelService
            , TypeService typeService, EngineService engineService, InvoiceService invoiceService, OrderService orderService, UserServiceImpl userService, TemplateEngine templateEngine) {
        this.invoiceService = invoiceService;
        this.orderService = orderService;
        this.userService = userService;
        this.templateEngine = templateEngine;
    }


    @GetMapping("user/invoices")
    String getUserInvoices(@AuthenticationPrincipal User userSec, Model model
            , @Param("keyword") String keyword) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("invoices", invoiceService.getAllByUser(userService
                .findUserByUserName(userSec.getUsername()),keyword));
        return "invoice/invoices-user";
    }

    @GetMapping("admin/invoices")
    String getAllInvoices(Model model
            , @Param("keyword") String keyword) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("invoices", invoiceService.getAll(keyword));
        return "invoice/invoices-admin";
    }

    @GetMapping("/invoices/{id}")
    public String getInvoiceForm(@PathVariable Integer id, Model model) {
        if (!invoiceService.existsById(id)) {
//            return "redirect:/invoices";
        }
        InvoiсeDto invoiсeDto = invoiceService.findInvoiceById(id);
        double hours = DatesUtil.returnDifferenceInHours(invoiсeDto.getOrder().getRentBegin(), invoiсeDto.getOrder().getRentEnd());
        model.addAttribute("hours", hours);
        model.addAttribute("invoice", invoiсeDto);
        model.addAttribute("toPdf",true);

        return "invoice/invoice-details";
    }

    @GetMapping("/invoices/{id}/pdf")
    public ResponseEntity<?> getPDF(@PathVariable Integer id,HttpServletRequest request, HttpServletResponse response) throws IOException {

        InvoiсeDto invoiсeDto = invoiceService.findInvoiceById(id);
        double hours = DatesUtil.returnDifferenceInHours(invoiсeDto.getOrder().getRentBegin(), invoiсeDto.getOrder().getRentEnd());

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("invoice", invoiсeDto);
        context.setVariable("hours", hours);
        String invoiceHtml = templateEngine.process("invoice/invoice-details", context);

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");
        /* Call convert method */
        HtmlConverter.convertToPdf(invoiceHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */
        String fileName = "attachment; filename=invoice " + invoiсeDto.getId() +".pdf";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }


}
