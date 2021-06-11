package by.academy.rentApp.controller;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.dto.EngineIdDto;
import by.academy.rentApp.dto.EngineNameDto;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/engines")
public class EngineController {
    private final EngineService engineService;

    public EngineController(EngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping
    public List<EngineDto> getEngins() {
        return engineService.getAll();
    }

    @GetMapping("/{id}")
    public EngineIdDto getEngineById(@PathVariable String id) {
        EngineIdDto engineIdDto = engineService.getEngineById(Integer.parseInt(id));
        return engineIdDto;
    }

    @GetMapping("/name")
    public EngineNameDto getEngineByName(@RequestParam("name") String name) {
        return engineService.getEngineByName(name);
    }
}
