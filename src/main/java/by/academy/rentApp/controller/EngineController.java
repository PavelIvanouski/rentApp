package by.academy.rentApp.controller;

import by.academy.rentApp.dto.EngineDto;
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
    public String  getEngins(Model model) {
        Iterable<EngineDto> engines = engineService.getAll();
        model.addAttribute("engines",engines);
        return "engines";
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
