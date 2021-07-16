package by.academy.rentApp.controller;

import by.academy.rentApp.service.CarModelService;
import by.academy.rentApp.service.CarService;
import by.academy.rentApp.service.EngineService;
import by.academy.rentApp.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final CarModelService carModelService;
    private final TypeService typeService;
    private final EngineService engineService;

    public MainController(CarModelService carModelService
            , TypeService typeService, EngineService engineService) {
        this.carModelService = carModelService;
        this.typeService = typeService;
        this.engineService = engineService;
    }

    @GetMapping("/")
    public String getMainPage(Model model) {
        model.addAttribute("models", carModelService.getAll());
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("engines", engineService.getAll());
        return "home";
    }

    @GetMapping("/test")
    public String getTestPage(Model model) {

        return "testImage";
    }

}
