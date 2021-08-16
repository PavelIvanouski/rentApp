package by.academy.rentApp.controller;

import by.academy.rentApp.dto.CarModelDto;
import by.academy.rentApp.model.entity.CarModel;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.CarModelService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(CarModelController.class);
    public static final String MODEL_EDIT = "model/model-edit";
    public static final String MODEL_ADD = "model/model-add";
    public static final String REDIRECT_MODELS = "redirect:/models";
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
        return MODEL_ADD;
    }

    @PostMapping("add")
    public String addModel(@Validated @ModelAttribute("model") CarModelDto carModelDto, BindingResult bindingResult
            , Model model) {
        if (carModelService.findModelByName(carModelDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.carModelDto",
                            "There is already a model with the model name provided");
            model.addAttribute("brands", brandService.getAll());
            return MODEL_ADD;
        }
        if (carModelDto.getBrand().getId() == null) {
            model.addAttribute("brandError", "Please, provide not empty brand");
            model.addAttribute("brands", brandService.getAll());
            return MODEL_ADD;
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", brandService.getAll());
            return MODEL_ADD;
        }
        LOGGER.debug("carModelService.saveModel called for " + carModelDto);
        CarModelDto savedModel = carModelService.saveModel(carModelDto);
        LOGGER.debug("New model added " + savedModel);
        return REDIRECT_MODELS;
    }

    @GetMapping("/edit/{id}")
    public String getModelEditForm(@PathVariable Integer id, Model model) {
        if (!carModelService.existsById(id)) {
            return REDIRECT_MODELS;
        }
        model.addAttribute("model", carModelService.findModelById(id));
        model.addAttribute("brands", brandService.getAll());
        return MODEL_EDIT;
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
            return MODEL_EDIT;
        }
        if (carModelDto.getBrand().getId() == null) {
            model.addAttribute("brandError", "Please, provide not empty brand");
            model.addAttribute("brands", brandService.getAll());
            return MODEL_EDIT;
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("brands", brandService.getAll());
            return MODEL_EDIT;
        }
        carModelService.saveModel(carModelDto);
        return REDIRECT_MODELS;
    }

    @PostMapping("{id}/delete")
    public String deleteModel(@RequestParam Integer id, Model model) {
        CarModelDto carModelDto = carModelService.findModelById(id);
        carModelService.deleteModel(carModelDto);
        return REDIRECT_MODELS;
    }

}
