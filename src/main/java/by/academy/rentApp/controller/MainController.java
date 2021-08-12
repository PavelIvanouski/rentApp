package by.academy.rentApp.controller;

import by.academy.rentApp.service.CarModelService;
import by.academy.rentApp.service.CarService;
import by.academy.rentApp.service.EngineService;
import by.academy.rentApp.service.TypeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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

    @GetMapping("/localhost:8080?lang=be")
    public String getMainPageBe(Model model) {
        model.addAttribute("models", carModelService.getAll());
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("engines", engineService.getAll());
        return "home";
    }

    @GetMapping("/test")
    public String getTestPage(Model model) {

        return "testImage";
    }

    @GetMapping("/contact")
    public String getContactPage(Model model) {
        return "contact";
    }

    @GetMapping("/about")
    public String getAboutPage(Model model) {
        return "about";
    }

}
