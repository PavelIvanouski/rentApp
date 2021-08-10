package by.academy.rentApp.controller;

import by.academy.rentApp.dto.InvoiсeDto;
import by.academy.rentApp.dto.OrderDto;
import by.academy.rentApp.service.*;
import by.academy.rentApp.util.DatesUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class InvoiceController {
    private final CarService carService;
    private final CarModelService carModelService;
    private final TypeService typeService;
    private final EngineService engineService;
    private final InvoiceService invoiceService;
    private final OrderService orderService;


    public InvoiceController(CarService carService, CarModelService carModelService
            , TypeService typeService, EngineService engineService, InvoiceService invoiceService, OrderService orderService) {
        this.carService = carService;
        this.carModelService = carModelService;
        this.typeService = typeService;
        this.engineService = engineService;
        this.invoiceService = invoiceService;
        this.orderService = orderService;
    }


    //    @GetMapping("")
//    public String getCars(Model model) {
//        List<CarDto> cars = carService.getAll();
//        model.addAttribute("cars", cars);
//        model.addAttribute("title", "Cars");
//        return "car/cars";
//    }
//
    @GetMapping("/admin/invoices/add/{id}")
    public String getInvoiceAddForm(@PathVariable Integer id,Model model) {
        if (!orderService.existsById(id)) {
            return "redirect: admin/invoices";
        }
        OrderDto orderDto = orderService.findOrderById(id);
        InvoiсeDto invoiсeDto = new InvoiсeDto();
        invoiсeDto.setOrder(orderDto);
        invoiсeDto.setSerialNumber(2);
        model.addAttribute("invoice",invoiсeDto);
        return "invoice/invoice-add";
    }

    @PostMapping("/admin/invoices/add/{id}")
    public String saveExtraInvoice(@ModelAttribute("invoice") InvoiсeDto invoiсeDto,Model model) {


        return "invoice/invoice-add";
    }
//
//    @GetMapping("all")
//    public String getAllCarsForm(Model model) {
//        List<CarDto> cars = carService.getAll();
//        model.addAttribute("cars", cars);
//        return "car/cars-all";
//    }
//
//    @PostMapping("add")
//    public String addCar(@Validated @ModelAttribute("car") CarDto carDto
//            , @RequestParam(value = "image", required = false) MultipartFile multipartFile, BindingResult bindingResult
//            , Model model) throws IOException {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("models", carModelService.getAll());
//            model.addAttribute("types", typeService.getAll());
//            model.addAttribute("engines", engineService.getAll());
//            return "car/car-add";
//        }
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        carDto.setPhotos(fileName);
//
//        CarDto savedCar = carService.saveCar(carDto);
//
//        String uploadDir = "./car-photos/" + savedCar.getId();
//
//        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//
//        return "redirect:/cars";
//    }
//
    @GetMapping("/invoices/{id}")
    public String getInvoiceForm(@PathVariable Integer id, @AuthenticationPrincipal User userSec, Model model) {
        if (!invoiceService.existsById(id)) {
//            return "redirect:/invoices";
        }
        InvoiсeDto invoiсeDto = invoiceService.findInvoiceById(id);
        model.addAttribute("user",invoiсeDto.getOrder().getUser());
        double hours = DatesUtil.returnDifferenceInHours(invoiсeDto.getOrder().getRentBegin(), invoiсeDto.getOrder().getRentEnd());
        model.addAttribute("hours",hours);
        model.addAttribute("invoice",invoiсeDto);

        return "invoice/invoice-details";
//        return "invoice/invoice-modal :: view";
    }
//
//    @PostMapping("{id}/edit")
//    public String updateCar(@Validated @ModelAttribute("car") CarDto carDto
//            , @RequestParam(value = "image", required = false) MultipartFile multipartFile, BindingResult bindingResult
//            , Model model) throws IOException {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("models", carModelService.getAll());
//            model.addAttribute("types", typeService.getAll());
//            model.addAttribute("engines", engineService.getAll());
//            return "car/car-edit";
//        }
//
//        if (multipartFile != null) {
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            carDto.setPhotos(fileName);
//
//            String uploadDir = "./car-photos/" + carDto.getId();
//
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//        }
//        CarDto carBeforeUpdating = carService.findCarById(carDto.getId());
//        carDto.setCreatingDate(carBeforeUpdating.getCreatingDate());
//        carService.saveCar(carDto);
//        return "redirect:/cars";
//    }
//
//
//    @GetMapping("{id}/details")
//    public String getCarDetailsForm(@PathVariable Integer id, Model model) {
//        if (!carService.existsById(id)) {
//            return "redirect:/cars/all";
//        }
////        CarDto car = carService.findCarById(id);
////        OrderDto order = new OrderDto();
////        order.setCar(car);
////        model.addAttribute("order", order);
//        model.addAttribute("car", carService.findCarById(id));
//        return "car/car-details";
//    }
//
//    @PostMapping("{id}/delete")
//    public String deleteCar(@RequestParam Integer id, Model model) {
//        CarDto carDto = carService.findCarById(id);
//        carService.deleteCar(carDto);
//        return "redirect:/cars";
//    }

}
