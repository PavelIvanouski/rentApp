package by.academy.rentApp.controller;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.MessagesDto;
import by.academy.rentApp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    private final CarModelService carModelService;
    private final TypeService typeService;
    private final EngineService engineService;
    private final MessagesService messagesService;

    public MainController(CarModelService carModelService
            , TypeService typeService, EngineService engineService, MessagesService messagesService) {
        this.carModelService = carModelService;
        this.typeService = typeService;
        this.engineService = engineService;
        this.messagesService = messagesService;
    }

    @GetMapping("/")
    public String getMainPage(Model model) {
        model.addAttribute("models", carModelService.getAll());
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("engines", engineService.getAll());
        return "home";
    }

//    @GetMapping("/localhost:8080?lang=be")
//    public String getMainPageBe(Model model) {
//        model.addAttribute("models", carModelService.getAll());
//        model.addAttribute("types", typeService.getAll());
//        model.addAttribute("engines", engineService.getAll());
//        return "home";
//    }

    @GetMapping("/test")
    public String getTestPage(Model model) {

        return "testImage";
    }



    @GetMapping("/about")
    public String getAboutPage(Model model) {
        return "about";
    }

}
