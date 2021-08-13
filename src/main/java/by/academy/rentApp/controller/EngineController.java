package by.academy.rentApp.controller;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.service.EngineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        return "engine/engines";
    }

    @GetMapping("add")
    public String getEngineAddForm(Model model) {
        model.addAttribute("engine", new EngineDto());
        return "engine/engine-add";
    }

    @PostMapping("add")
    public String addEngine(@Validated @ModelAttribute("engine") EngineDto engineDto, BindingResult bindingResult
            , Model model) {
        if (engineService.findEngineByName(engineDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.EngineDto",
                            "There is already a engine with the engine name provided");
            return "engine/engine-add";
        }
        if (bindingResult.hasErrors()) {
            return "engine/engine-add";
        }
        engineService.saveEngine(engineDto);
        return "redirect:/engines";
    }

    @GetMapping("/edit/{id}")
    public String getEngineEditForm(@PathVariable Integer id, Model model) {
        if (!engineService.existsById(id)) {
            return "redirect:/engines";
        }
        model.addAttribute("engine", engineService.findEngineById(id));
        return "engine/engine-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateEngine(@Validated @ModelAttribute("engine") EngineDto engineDto, BindingResult bindingResult
            , Model model) {
        if (engineService.findEngineByName(engineDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.EngineDto",
                            "There is already a engine with the engine name provided");
            return "engine/engine-edit";
        }
        if (bindingResult.hasErrors()) {
            return "engine/engine-edit";
        }
        engineService.saveEngine(engineDto);
        return "redirect:/engines";
    }

    @PostMapping("{id}/delete")
    public String deleteEngine(@RequestParam Integer id, Model model) {
        EngineDto engineDto = engineService.findEngineById(id);
        engineService.deleteEngine(engineDto);
        return "redirect:/engines";
    }

}
