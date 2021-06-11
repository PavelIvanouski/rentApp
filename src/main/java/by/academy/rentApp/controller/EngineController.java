package by.academy.rentApp.controller;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.dto.EngineIdDto;
import by.academy.rentApp.dto.EngineNameDto;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EngineController {
    private final EngineService engineService;

    public EngineController(EngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping("/engins")
    public List<EngineDto> getEngins() {
        return engineService.getAll();
    }

    @GetMapping("/engineId")
    public EngineIdDto getEngineById(@RequestParam("id") Integer id) {
        return engineService.getEngineById(id);
    }

    @GetMapping("/engineName")
    public EngineNameDto getEngineByName(@RequestParam("name") String name) {
        return engineService.getEngineByName(name);
    }
}
