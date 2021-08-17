package by.academy.rentApp.controller;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.exception.AppException;
import by.academy.rentApp.service.BrandService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);
    public static final String BRAND_EDIT = "brand/brand-edit";
    public static final String BRAND_ADD = "brand/brand-add";
    public static final String REDIRECT_BRANDS = "redirect:/brands";
    public static final String POST_URL = "postURL";
    public static final String POST_URL_EDIT = "/brands/edit";
    public static final String POST_URL_ADD = "/brands/add";
    public static final String ADD = "add";
    public static final String UPDATE = "update";
    public static final String TITLE = "title";

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("")
    public String getBrands(Model model) throws AppException {
        List<BrandDto> brands = brandService.getAll();

        String s = "throw";
        //voluntarily throw exception
        if(s.equals("throw")){
            throw new AppException("Exception example");
        }

        model.addAttribute("brands", brands);
        model.addAttribute("title", "Brands");
        return "brand/brands";
    }

    @GetMapping("add")
    public String getBrandAddForm(Model model) {
        model.addAttribute("brand", new BrandDto());
        model.addAttribute(TITLE, ADD);
        model.addAttribute(POST_URL, POST_URL_ADD);
        return BRAND_ADD;
    }

    @PostMapping("add")
    public String addBrand(@Validated @ModelAttribute("brand") BrandDto brandDto, BindingResult bindingResult
            , Model model) {
        if (brandService.findBrandByName(brandDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.BrandDto",
                            "There is already a brand with the brand name provided");
            model.addAttribute(TITLE, "Add");
            model.addAttribute(POST_URL, POST_URL_ADD);
            return BRAND_ADD;
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute(TITLE, ADD);
            model.addAttribute(POST_URL, POST_URL_ADD);
            return BRAND_ADD;
        }
        LOGGER.debug("brandService.saveBrand called for " + brandDto);
        BrandDto savedBrand = brandService.saveBrand(brandDto);
        LOGGER.debug("New brand added " + savedBrand);
        return REDIRECT_BRANDS;
    }

    @GetMapping("/edit/{id}")
    public String getBrandEditForm(@PathVariable Integer id, Model model) {
        if (!brandService.existsById(id)) {
            return REDIRECT_BRANDS;
        }
        model.addAttribute("brand", brandService.findBrandById(id));
        model.addAttribute(POST_URL, POST_URL_EDIT + id);
        model.addAttribute(TITLE, UPDATE);
        return BRAND_EDIT;
    }

    @PostMapping("/edit/{id}")
    public String updateBrand(@Validated @ModelAttribute("brand") BrandDto brandDto, BindingResult bindingResult
            , Model model) {
        if (brandService.findBrandByName(brandDto.getName()) != null) {
            bindingResult
                    .rejectValue("name", "error.BrandDto",
                            "There is already a brand with the brand name provided");
            model.addAttribute(TITLE, UPDATE);
            model.addAttribute(POST_URL, POST_URL_EDIT + brandDto.getId());
            return BRAND_EDIT;
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute(TITLE, UPDATE);
            model.addAttribute(POST_URL, POST_URL_EDIT + brandDto.getId());
            return BRAND_EDIT;
        }
        LOGGER.debug("brandService.saveBrand called for " + brandDto);
        BrandDto savedBrand = brandService.saveBrand(brandDto);
        LOGGER.debug("Brand edited " + savedBrand);
        return REDIRECT_BRANDS;
    }

    @PostMapping("{id}/delete")
    public String deleteBrand(@RequestParam Integer id, Model model) {
        BrandDto brandDto = brandService.findBrandById(id);
        brandService.deleteBrand(brandDto);
        return REDIRECT_BRANDS;
    }

}
