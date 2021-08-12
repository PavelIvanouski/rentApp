package by.academy.rentApp.controller;

import by.academy.rentApp.dto.MessagesDto;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.MessagesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class MessagesController {
    private final MessagesService messagesService;

    public MessagesController(BrandService brandService, MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping("/contact")
    public String getContactPage(Model model) {
        model.addAttribute("message", new MessagesDto());
        return "contact";
    }

    @PostMapping("/contact")
    public String getContactMessage(@Validated @ModelAttribute("message") MessagesDto messagesDto
            , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "contact";
        }
        messagesService.saveMessage(messagesDto);
        model.addAttribute("successMessage", "Your message was sent successfully.");
        model.addAttribute("message", new MessagesDto());
        return "contact";
    }


    @GetMapping("/admin/messages")
    public String getMessages(Model model) {
        model.addAttribute("messages", messagesService.getAll());
        return "messages/messages";
    }

    @GetMapping("/admin/messages/{id}")
    public String getMessageForm(@PathVariable Integer id, Model model) {
        if (!messagesService.existsById(id)) {
            return "redirect:/admin/messages";
        }
        model.addAttribute("message", messagesService.findMessageById(id));
        return "messages/message-view";
    }


}
