package by.academy.rentApp.controller;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.EngineService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private static final Logger logger = LogManager.getLogger(BrandController.class);
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
        return "brand/brand-add";
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
            return "brand/brand-add";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Add");
            model.addAttribute("postURL", "/brands/add");
            return "brand/brand-add";
        }
        BrandDto savedBrand = brandService.saveBrand(brandDto);
        logger.info("New brand added id=" + savedBrand.getId());
        return "redirect:/brands";
    }

    @GetMapping("/edit/{id}")
    public String getBrandEditForm(@PathVariable Integer id, Model model) {
        if (!brandService.existsById(id)) {
            return "redirect:/brands";
        }
        model.addAttribute("brand", brandService.findBrandById(id));
        model.addAttribute("postURL", "/brands/edit" + id);
        model.addAttribute("title", "Update");
        return "brand/brand-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBrand(@Validated @ModelAttribute("brand") BrandDto brandDto, BindingResult bindingResult
            , Model model) {
        if (brandService.findBrandByName(brandDto.getName()) != null) {
            logger.info("Attempt to edit brand id=" + brandDto.getId());
//            logger.debug("Debugging log");
//            logger.info("Info log");
//            logger.warn("Hey, This is a warning!");
//            logger.error("Oops! We have an Error. OK");
//            logger.fatal("Damn! Fatal error. Please fix me.");
            bindingResult
                    .rejectValue("name", "error.BrandDto",
                            "There is already a brand with the brand name provided");
            model.addAttribute("title", "Update");
            model.addAttribute("postURL", "/brands/edit" + brandDto.getId());
            return "brand/brand-edit";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Update");
            model.addAttribute("postURL", "/brands/edit" + brandDto.getId());
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
