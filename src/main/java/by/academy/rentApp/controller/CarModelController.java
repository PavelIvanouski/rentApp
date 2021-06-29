package by.academy.rentApp.controller;

import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.CarModelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/models")
public class CarModelController {
    private final CarModelService carModelService;
    private final BrandService brandService;


    public CarModelController(CarModelService carModelService, BrandService brandService) {
        this.carModelService = carModelService;
        this.brandService = brandService;
    }

    @GetMapping("")
    public String getModels(Model model) {
        List<CarModelDto> models = carModelService.getAll();
        model.addAttribute("models", models);
        model.addAttribute("title", "Models");
        return "models";
    }

    @GetMapping("add")
    public String getCarModelAddForm(Model model) {
        model.addAttribute("model", new CarModelDto());
        model.addAttribute("brands", brandService.getAll());
        return "model-add";
    }

    @PostMapping("add")
    public String addModel(@Validated @ModelAttribute("model") CarModelDto carModelDto, BindingResult bindingResult
            , Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", brandService.getAll());
            return "model-add";
        }
        carModelService.saveModel(carModelDto);
        return "redirect:/models";
    }

    @GetMapping("{id}/edit")
    public String getModelEditForm(@PathVariable Integer id, Model model) {
        if (!carModelService.existsById(id)) {
            return "redirect:/models";
        }
        model.addAttribute("model", carModelService.findModelById(id));
        model.addAttribute("brands", brandService.getAll());
        return "model-edit";
    }

    @PostMapping("{id}/edit")
    public String updateModel(@Validated @ModelAttribute("model") CarModelDto carModelDto, BindingResult bindingResult
            , Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", brandService.getAll());
            return "model-edit";
        }
        carModelService.saveModel(carModelDto);
        return "redirect:/models";
    }

    @PostMapping("{id}/delete")
    public String deleteModel(@RequestParam Integer id, Model model) {
        CarModelDto carModelDto = carModelService.findModelById(id);
        carModelService.deleteModel(carModelDto);
        return "redirect:/models";
    }

}
