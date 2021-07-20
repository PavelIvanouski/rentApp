package by.academy.rentApp.controller;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.EngineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("")
    public String getBrands(Model model) {
        List<BrandDto> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        model.addAttribute("title", "Brands");
        return "brand/brands";
    }

    @GetMapping("add")
    public String getBrandAddForm(Model model) {
        model.addAttribute("brand", new BrandDto());
        model.addAttribute("title", "Add");
        model.addAttribute("postURL", "/brands/add");
        return "brand/brand-edit";
    }

    @PostMapping("add")
    public String addBrand(@Validated @ModelAttribute("brand") BrandDto brandDto, BindingResult bindingResult
            , Model model) {
        if (brandService.findBrandByName(brandDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.BrandDto",
                            "There is already a brand with the brand name provided");
            model.addAttribute("title", "Add");
            model.addAttribute("postURL", "/brands/add");
            return "brand/brand-edit";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Add");
            model.addAttribute("postURL", "/brands/add");
            return "brand/brand-edit";
        }
        brandService.saveBrand(brandDto);
        return "redirect:/brands";
    }

    @GetMapping("{id}/edit")
    public String getBrandEditForm(@PathVariable Integer id, Model model) {
        if (!brandService.existsById(id)) {
            return "redirect:/brands";
        }
        model.addAttribute("brand", brandService.findBrandById(id));
        model.addAttribute("postURL", "/brands/" + id + "/edit");
        model.addAttribute("title", "Update");
        return "brand/brand-edit";
    }

    @PostMapping("{id}/edit")
    public String updateBrand(@Validated @ModelAttribute("brand") BrandDto brandDto, BindingResult bindingResult
            , Model model) {
        if (brandService.findBrandByName(brandDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.BrandDto",
                            "There is already a brand with the brand name provided");
            model.addAttribute("title", "Update");
            model.addAttribute("postURL", "/brands/" + brandDto.getId() + "/edit");
            return "brand/brand-edit";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Update");
            model.addAttribute("postURL", "/brands/" + brandDto.getId() + "/edit");
            return "brand/brand-edit";
        }
        brandService.saveBrand(brandDto);
        return "redirect:/brands";
    }

    @PostMapping("{id}/delete")
    public String deleteBrand(@RequestParam Integer id, Model model) {
        BrandDto brandDto = brandService.findBrandById(id);
        brandService.deleteBrand(brandDto);
        return "redirect:/brands";
    }

}
