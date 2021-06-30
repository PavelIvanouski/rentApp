package by.academy.rentApp.controller;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.RoleDto;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getRoles(Model model) {
        List<RoleDto> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        model.addAttribute("title", "Roles");
        return "roles";
    }

    @GetMapping("add")
    public String getRoleAddForm(Model model) {
        model.addAttribute("role", new RoleDto());
        model.addAttribute("title", "Add");
        model.addAttribute("postURL", "/roles/add");
        return "role-edit";
    }

    @PostMapping("add")
    public String addRole(@Validated @ModelAttribute("role") RoleDto roleDto, BindingResult bindingResult
            , Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Add");
            model.addAttribute("postURL", "/roles/add");
            return "role-edit";
        }
        roleService.saveRole(roleDto);
        return "redirect:/roles";
    }

    @GetMapping("{id}/edit")
    public String getRoleEditForm(@PathVariable Integer id, Model model) {
        if (!roleService.existsById(id)) {
            return "redirect:/roles";
        }
        model.addAttribute("role", roleService.findRoleById(id));
        model.addAttribute("postURL", "/roles/" + id + "/edit");
        model.addAttribute("title", "Update");
        return "role-edit";
    }

    @PostMapping("{id}/edit")
    public String updateRole(@Validated @ModelAttribute("role") RoleDto roleDto, BindingResult bindingResult
            , Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Update");
            model.addAttribute("postURL", "/roles/" + roleDto.getId() + "/edit");
            return "role-edit";
        }
        roleService.saveRole(roleDto);
        return "redirect:/roles";
    }

    @PostMapping("{id}/delete")
    public String deleteRole(@RequestParam Integer id, Model model) {
        RoleDto roleDto = roleService.findRoleById(id);
        roleService.deleteRole(roleDto);
        return "redirect:/roles";
    }

}
