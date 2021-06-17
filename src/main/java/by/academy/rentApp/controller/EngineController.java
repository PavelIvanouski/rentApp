package by.academy.rentApp.controller;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.exception.ValidationException;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.service.EngineService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/engines")
public class EngineController {
    private final EngineService engineService;

    public EngineController(EngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping("")
    public String getEngines(Model model) {
        List<EngineDto> engines = engineService.getAll();
//        model.addAttribute("engine", new EngineDto());
        model.addAttribute("engines", engines);
        return "engines";
    }

    @GetMapping("add")
    public String engineAdd(Model model) {
        return "engine-add";
    }

    @PostMapping("add")
    public String engineAdd(@RequestParam String name, Model model) throws ValidationException {
        EngineDto engineDto = new EngineDto();
        engineDto.setName(name);
        engineService.saveEngine(engineDto);
        return "redirect:/engines";
    }

    @GetMapping("{id}/edit")
    public String engineEdit(@PathVariable Integer id, Model model) {
        if (!engineService.existsById(id)) {
            return "redirect:/";
        }
        Optional<EngineDto> engineDto = Optional.ofNullable(engineService.findEngineById(id));
        ArrayList<EngineDto> res = new ArrayList<>();
        engineDto.ifPresent(res::add);
        model.addAttribute("engine", res);
        return "engine-edit";
    }

    @PostMapping("{id}/edit")
    public String engineUpdate(@RequestParam Integer id, @RequestParam String name, Model model) throws ValidationException {
        EngineDto engineDto = engineService.findEngineById(id);
        engineDto.setName(name);
        engineService.saveEngine(engineDto);
        return "redirect:/engines";
    }

    @PostMapping("{id}/delete")
    public String engineDelete(@RequestParam Integer id, Model model) throws ValidationException {
        EngineDto engineDto = engineService.findEngineById(id);
        engineService.deleteEngine(engineDto);
        return "redirect:/engines";
    }

//
//    @GetMapping("/name")
//    public EngineNameDto getEngineByName(@RequestParam("name") String name) {
//        return engineService.getEngineByName(name);
//    }
}
