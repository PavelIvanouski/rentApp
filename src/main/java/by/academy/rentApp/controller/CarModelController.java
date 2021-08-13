package by.academy.rentApp.controller;

import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.model.entity.CarModel;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.CarModelService;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
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
        return "model/models";
    }

    @GetMapping("add")
    public String getCarModelAddForm(Model model) {
        model.addAttribute("model", new CarModelDto());
        model.addAttribute("brands", brandService.getAll());
        return "model/model-add";
    }

    @PostMapping("add")
    public String addModel(@Validated @ModelAttribute("model") CarModelDto carModelDto, BindingResult bindingResult
            , Model model) {
        if (carModelService.findModelByName(carModelDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.carModelDto",
                            "There is already a model with the model name provided");
            model.addAttribute("brands", brandService.getAll());
            return "model/model-add";
        }
        if (carModelDto.getBrand().getId() == null) {
            model.addAttribute("brandError", "Please, provide not empty brand");
            model.addAttribute("brands", brandService.getAll());
            return "model/model-add";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", brandService.getAll());
            return "model/model-add";
        }
        carModelService.saveModel(carModelDto);
        return "redirect:/models";
    }

    @GetMapping("/edit/{id}")
    public String getModelEditForm(@PathVariable Integer id, Model model) {
        if (!carModelService.existsById(id)) {
            return "redirect:/models";
        }
        model.addAttribute("model", carModelService.findModelById(id));
        model.addAttribute("brands", brandService.getAll());
        return "model/model-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateModel(@NotNull @Validated @ModelAttribute("model") CarModelDto carModelDto
//            ,@RequestParam(name = "creatingD") String creatingD
            , BindingResult bindingResult
            , Model model) {
        CarModelDto updatingCar = carModelService.findModelById(carModelDto.getId());
        carModelDto.setCreatingDate(updatingCar.getCreatingDate());
        carModelDto.setUpdatingDate(updatingCar.getUpdatingDate());
        if (carModelService.findModelByName(carModelDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.carModelDto",
                            "There is already a model with the model name provided");
            model.addAttribute("brands", brandService.getAll());
            return "model/model-edit";
        }
        if (carModelDto.getBrand().getId() == null) {
            model.addAttribute("brandError", "Please, provide not empty brand");
            model.addAttribute("brands", brandService.getAll());
            return "model/model-edit";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", brandService.getAll());
            return "model/model-edit";
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
