package by.academy.rentApp.controller;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
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

    @GetMapping("")
    public String getCars(Model model) {
        List<CarDto> cars = carService.getAll();
        model.addAttribute("cars", cars);
        model.addAttribute("title", "Cars");
        return "car/cars";
    }

    @GetMapping("add")
    public String getCarAddForm(Model model) {
        model.addAttribute("car", new CarDto());
        model.addAttribute("models", carModelService.getAll());
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("engines", engineService.getAll());
        return "car/car-add";
    }

    @GetMapping("all")
    public String getAllCarsForm(Model model) {
        List<CarDto> cars = carService.getAll();
        model.addAttribute("cars", cars);
        return "car/cars-all";
    }

    @PostMapping("add")
    public String addCar(@Validated @ModelAttribute("car") CarDto carDto, BindingResult bindingResult
            , Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("models", carModelService.getAll());
            model.addAttribute("types", typeService.getAll());
            model.addAttribute("engines", engineService.getAll());
            return "car/car-add";
        }
        carService.saveCar(carDto);
        return "redirect:/cars";
    }

    @GetMapping("{id}/edit")
    public String getCarEditForm(@PathVariable Integer id, Model model) {
        if (!carService.existsById(id)) {
            return "redirect:/cars";
        }
        model.addAttribute("car", carService.findCarById(id));
        model.addAttribute("models", carModelService.getAll());
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("engines", engineService.getAll());
        return "car/car-edit";
    }

    @PostMapping("{id}/edit")
    public String updateCar(@Validated @ModelAttribute("car") CarDto carDto, BindingResult bindingResult
            , Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("models", carModelService.getAll());
            model.addAttribute("types", typeService.getAll());
            model.addAttribute("engines", engineService.getAll());
            return "car/car-edit";
        }
        carService.saveCar(carDto);
        return "redirect:/cars";
    }


    @GetMapping("{id}/details")
    public String getCarDetailsForm(@PathVariable Integer id, Model model) {
        if (!carService.existsById(id)) {
            return "redirect:/cars/all";
        }
        model.addAttribute("car", carService.findCarById(id));
//        model.addAttribute("models", carModelService.getAll());
//        model.addAttribute("types", typeService.getAll());
//        model.addAttribute("engines", engineService.getAll());
        return "car/car-details";
    }

    @PostMapping("{id}/delete")
    public String deleteCar(@RequestParam Integer id, Model model) {
        CarDto carDto = carService.findCarById(id);
        carService.deleteCar(carDto);
        return "redirect:/cars";
    }

}
