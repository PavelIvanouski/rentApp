package by.academy.rentApp.controller;

import by.academy.rentApp.dto.CarDto;
import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.CarModelService;
import by.academy.rentApp.service.CarService;
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


    public CarController(CarService carService, CarModelService carModelService) {
        this.carService = carService;
        this.carModelService = carModelService;
    }

    @GetMapping("")
    public String getCars(Model model) {
        List<CarDto> cars = carService.getAll();
        model.addAttribute("cars", cars);
        model.addAttribute("title", "Cars");
        return "cars";
    }

    @GetMapping("add")
    public String getCarAddForm(Model model) {
        model.addAttribute("car", new CarDto());
        model.addAttribute("models", carModelService.getAll());
        return "car-add";
    }
//
//    @PostMapping("add")
//    public String addModel(@Validated @ModelAttribute("model") CarModelDto carModelDto, BindingResult bindingResult
//            , Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("brands", brandService.getAll());
//            return "model-add";
//        }
//        carModelService.saveModel(carModelDto);
//        return "redirect:/models";
//    }
//
//    @GetMapping("{id}/edit")
//    public String getModelEditForm(@PathVariable Integer id, Model model) {
//        if (!carModelService.existsById(id)) {
//            return "redirect:/models";
//        }
//        model.addAttribute("model", carModelService.findModelById(id));
//        model.addAttribute("brands", brandService.getAll());
//        return "model-edit";
//    }
//
//    @PostMapping("{id}/edit")
//    public String updateModel(@Validated @ModelAttribute("model") CarModelDto carModelDto, BindingResult bindingResult
//            , Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("brands", brandService.getAll());
//            return "model-edit";
//        }
//        carModelService.saveModel(carModelDto);
//        return "redirect:/models";
//    }
//
//    @PostMapping("{id}/delete")
//    public String deleteModel(@RequestParam Integer id, Model model) {
//        CarModelDto carModelDto = carModelService.findModelById(id);
//        carModelService.deleteModel(carModelDto);
//        return "redirect:/models";
//    }

}
