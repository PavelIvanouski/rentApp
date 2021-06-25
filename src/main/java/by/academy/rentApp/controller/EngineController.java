package by.academy.rentApp.controller;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.exception.ValidationException;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.service.EngineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("")
    public String engineAddOrUpdate(@Validated @ModelAttribute EngineDto engineDto, BindingResult bindingResult
            , Model model) throws ValidationException {
        if (bindingResult.hasErrors()) {
//            model.addAttribute("engine",engineDto);
//            model.addAttribute("title","Update");
            return "engine-edit";
        }
        engineService.saveEngine(engineDto);
        return "redirect:/engines";
    }

    @GetMapping("add")
    public String engineAddOrUpdate(Model model) {
        model.addAttribute("engine", new EngineDto());
        return "engine-add";
    }

    @PostMapping("add")
    public String engineAddPost(@Validated @ModelAttribute("engine") EngineDto engineDto, BindingResult bindingResult
            , Model model) throws ValidationException {
        if (bindingResult.hasErrors()) {
            return "engine-add";
        }
        engineService.saveEngine(engineDto);
        return "redirect:/engines";
    }

    @GetMapping("{id}/edit")
    public String engineEdit(@PathVariable Integer id, Model model) {
        if (!engineService.existsById(id)) {
            return "redirect:/engines";
        }
        model.addAttribute("title", "Update");
        model.addAttribute("engine", engineService.findEngineById(id));
        return "engine-edit";
    }

    @PostMapping("{id}/delete")
    public String engineDelete(@RequestParam Integer id, Model model) throws ValidationException {
        EngineDto engineDto = engineService.findEngineById(id);
        engineService.deleteEngine(engineDto);
        return "redirect:/engines";
    }

}
