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
        model.addAttribute("engines", engines);
        model.addAttribute("title", "Engines");
        return "engines";
    }

    @GetMapping("add")
    public String engineAddOrUpdate(Model model) {
        model.addAttribute("engine", new EngineDto());
        return "engine-add";
    }

//    @PostMapping("add")
//    public String engineAdd(@ModelAttribute EngineDto engineDto, Model model) throws ValidationException {
//        engineService.saveEngine(engineDto);
//        return "redirect:/engines";
//    }

//    @PostMapping("")
//    public String engineAddOrUpdate(@RequestParam String name, Model model) throws ValidationException {
//        EngineDto engineDto = new EngineDto();
//        engineDto.setName(name);
//        engineService.saveEngine(engineDto);
//        return "redirect:/engines";
//    }

//
//    @PostMapping("{id}/edit")
//    public String engineUpdate(@ModelAttribute EngineDto engineDto, Model model) throws ValidationException {
//        engineService.saveEngine(engineDto);
//        return "redirect:/engines";
//    }

    @PostMapping("")
    public String engineAddOrUpdate(@ModelAttribute EngineDto engineDto, Model model) throws ValidationException {
        engineService.saveEngine(engineDto);
        return "redirect:/engines";
    }

    @GetMapping("{id}/edit")
    public String engineEdit(@PathVariable Integer id, Model model) {
        if (!engineService.existsById(id)) {
            return "redirect:/engines";
        }
        model.addAttribute("engine", engineService.findEngineById(id));
//        Optional<EngineDto> engineDto = Optional.ofNullable(engineService.findEngineById(id));
//        ArrayList<EngineDto> res = new ArrayList<>();
//        engineDto.ifPresent(res::add);
//        model.addAttribute("engine", res);
        return "engine-edit";
    }


    @PostMapping("{id}/delete")
    public String engineDelete(@RequestParam Integer id, Model model) throws ValidationException {
        EngineDto engineDto = engineService.findEngineById(id);
        engineService.deleteEngine(engineDto);
        return "redirect:/engines";
    }

}
