package by.academy.rentApp.controller;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.exception.ValidationException;
import by.academy.rentApp.service.EngineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class EngineController {
    private final EngineService engineService;

    public EngineController(EngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping("/engines")
    public String getEngines(Model model) {
        List<EngineDto> engines = engineService.getAll();
        model.addAttribute("engine", new EngineDto());
        model.addAttribute("engines", engines);
        return "engines";
    }

    @PostMapping("/createEngine")
    public String createOrder(@ModelAttribute EngineDto engineDto) throws ValidationException {
        EngineDto savedEngine = engineService.saveEngine(engineDto);
        return "redirect:/engines";
    }
//    @GetMapping("/{id}")
//    public EngineIdDto getEngineById(@PathVariable String id) {
//        EngineIdDto engineIdDto = engineService.getEngineById(Integer.parseInt(id));
//        return engineIdDto;
//    }
//
//    @GetMapping("/name")
//    public EngineNameDto getEngineByName(@RequestParam("name") String name) {
//        return engineService.getEngineByName(name);
//    }
}
