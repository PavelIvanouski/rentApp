package by.academy.rentApp.controller;

import by.academy.rentApp.dto.TypeDto;
import by.academy.rentApp.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }
    @GetMapping("/types")
    public List<TypeDto> getTypes() {
        return typeService.getAll();
    }
}
