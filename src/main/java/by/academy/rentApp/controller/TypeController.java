package by.academy.rentApp.controller;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.dto.TypeDto;
import by.academy.rentApp.service.EngineService;
import by.academy.rentApp.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/types")
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("")
    public String getTypes(Model model) {
        List<TypeDto> types = typeService.getAll();
        model.addAttribute("types", types);
        model.addAttribute("title", "Types");
        return "type/types";
    }

    @GetMapping("add")
    public String getTypeAddForm(Model model) {
        model.addAttribute("type", new TypeDto());
        return "type/type-add";
    }

    @PostMapping("add")
    public String addType(@Validated @ModelAttribute("type") TypeDto typeDto, BindingResult bindingResult
            , Model model) {
        if (typeService.findTypeByName(typeDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.TypeDto",
                            "There is already a type with the type name provided");
            return "type/type-add";
        }
        if (bindingResult.hasErrors()) {
            return "type/type-add";
        }
        typeService.saveType(typeDto);
        return "redirect:/types";
    }

    @GetMapping("/edit/{id}")
    public String getTypeEditForm(@PathVariable Integer id, Model model) {
        if (!typeService.existsById(id)) {
            return "redirect:/types";
        }
        model.addAttribute("type", typeService.findTypeById(id));
        return "type/type-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateType(@Validated @ModelAttribute("type") TypeDto typeDto, BindingResult bindingResult
            , Model model) {
        if (typeService.findTypeByName(typeDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.TypeDto",
                            "There is already a type with the type name provided");
            return "type/type-edit";
        }
        if (bindingResult.hasErrors()) {
            return "type/type-edit";
        }
        typeService.saveType(typeDto);
        return "redirect:/types";
    }

    @PostMapping("{id}/delete")
    public String deleteType(@RequestParam Integer id, Model model) {
        TypeDto typeDto = typeService.findTypeById(id);
        typeService.deleteType(typeDto);
        return "redirect:/types";
    }

}
