package by.academy.rentApp.controller;

import by.academy.rentApp.dto.*;
import by.academy.rentApp.service.*;
import by.academy.rentApp.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CarController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;
    private final CarModelService carModelService;
    private final TypeService typeService;
    private final EngineService engineService;


    public CarController(CarService carService, CarModelService carModelService
            , TypeService typeService, EngineService engineService) {
        this.carService = carService;
        this.carModelService = carModelService;
        this.typeService = typeService;
        this.engineService = engineService;
    }

    @GetMapping("/cars/all")
    public String getAllCarsForm(Model model
            , @Param("modelId") Integer modelId
            , @Param("typeId") Integer typeId
            , @Param("engineId") Integer engineId) {
//        List<CarDto> cars = carService.getAll(keyword, typeId);
        List<CarDto> cars = carService.getAll(modelId, typeId, engineId);
        List<CarModelDto> modelDtos = carModelService.getAll();
        model.addAttribute("models", modelDtos);

        List<TypeDto> types = typeService.getAll();
        model.addAttribute("types", types);

        List<EngineDto> engineDtos = engineService.getAll();
        model.addAttribute("engines", engineDtos);
        model.addAttribute("cars", cars);
        model.addAttribute("modelId", modelId);
        model.addAttribute("typeId", typeId);
        model.addAttribute("engineId", engineId);
        return "car/cars-all";
    }

    @GetMapping("/admin/cars")
    public String getCars(Model model, @Param("keyword") String keyword
            , @Param("modelId") Integer modelId
            , @Param("typeId") Integer typeId
            , @Param("engineId") Integer engineId
            , @Param("rBegin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rBegin) {
        List<CarDto> cars = carService.getAll(modelId, typeId, engineId);

        List<CarModelDto> modelDtos = carModelService.getAll();
        model.addAttribute("models", modelDtos);

        List<TypeDto> types = typeService.getAll();
        model.addAttribute("types", types);

        List<EngineDto> engineDtos = engineService.getAll();
        model.addAttribute("engines", engineDtos);

        model.addAttribute("cars", cars);
        model.addAttribute("title", "Cars");
        model.addAttribute("keyword", keyword);
        model.addAttribute("modelId", modelId);
        model.addAttribute("typeId", typeId);
        model.addAttribute("engineId", engineId);
        model.addAttribute("rBegin", rBegin);
        return "car/cars";
    }

    @GetMapping("/admin/cars/add")
    public String getCarAddForm(Model model) {
        model.addAttribute("car", new CarDto());
        model.addAttribute("models", carModelService.getAll());
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("engines", engineService.getAll());
        return "car/car-add";
    }

    @PostMapping("/admin/cars/add")
    public String addCar(@Validated @ModelAttribute("car") CarDto carDto
            , @RequestParam(value = "image", required = false) MultipartFile multipartFile, BindingResult bindingResult
            , Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("models", carModelService.getAll());
            model.addAttribute("types", typeService.getAll());
            model.addAttribute("engines", engineService.getAll());
            return "car/car-add";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        carDto.setPhotos(fileName);

        LOGGER.debug("carService.saveCar called for " + carDto);
        CarDto savedCar = carService.saveCar(carDto);
        LOGGER.debug("Car added " + savedCar);

        String uploadDir = "./car-photos/" + savedCar.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/admin/cars";
    }

    @GetMapping("/admin/cars/edit/{id}")
    public String getCarEditForm(@PathVariable Integer id, Model model) {
        if (!carService.existsById(id)) {
            return "redirect:/admin/cars";
        }
        model.addAttribute("car", carService.findCarById(id));
        model.addAttribute("models", carModelService.getAll());
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("engines", engineService.getAll());
        return "car/car-edit";
    }

    @PostMapping("/admin/cars/edit/{id}")
    public String updateCar(@Validated @ModelAttribute("car") CarDto carDto
            , @RequestParam(value = "image", required = false) MultipartFile multipartFile, BindingResult bindingResult
            , Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("models", carModelService.getAll());
            model.addAttribute("types", typeService.getAll());
            model.addAttribute("engines", engineService.getAll());
            return "car/car-edit";
        }

        if (multipartFile != null) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            carDto.setPhotos(fileName);

            String uploadDir = "./car-photos/" + carDto.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        CarDto carBeforeUpdating = carService.findCarById(carDto.getId());
        carDto.setCreatingDate(carBeforeUpdating.getCreatingDate());
        carService.saveCar(carDto);
        return "redirect:/admin/cars";
    }


    @GetMapping("/cars/details/{id}")
    public String getCarDetailsForm(@PathVariable Integer id, Model model) {
        if (!carService.existsById(id)) {
            return "redirect:/cars/all";
        }
//        CarDto car = carService.findCarById(id);
//        OrderDto order = new OrderDto();
//        order.setCar(car);
//        model.addAttribute("order", order);
        model.addAttribute("car", carService.findCarById(id));
        return "car/car-details";
    }

    @PostMapping("{id}/delete")
    public String deleteCar(@RequestParam Integer id, Model model) {
        CarDto carDto = carService.findCarById(id);
        carService.deleteCar(carDto);
        return "redirect:/admin/cars";
    }

}
