package by.academy.rentApp.controller;

import by.academy.rentApp.dto.ModelDto;
import by.academy.rentApp.service.ModelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/models")
public class ModelController {
    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }
    @GetMapping
    public List<ModelDto> getModels() {
        return modelService.getAll();
    }
    @GetMapping("/model")
    public ModelDto getModel(@RequestParam("id") Integer id) {
        return modelService.getModel(id);
    }
}
