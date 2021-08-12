package by.academy.rentApp.controller;

import by.academy.rentApp.dto.MessagesDto;
import by.academy.rentApp.service.BrandService;
import by.academy.rentApp.service.MessgesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class MessagesController {
    private final MessgesService messagesService;

    public MessagesController(BrandService brandService, MessgesService messgesService) {
        this.messagesService = messgesService;
    }

    @GetMapping("/messages")
    public String getMessages(Model model) {
        model.addAttribute("messages", messagesService.getAll());
        return "messages/messages";
    }

    @GetMapping("/messages/{id}")
    public String getMessageForm(@PathVariable Integer id, Model model) {
        if (!messagesService.existsById(id)) {
            return "redirect:/admin/messages";
        }
        model.addAttribute("message", messagesService.findMessageById(id));
        return "messages/message-view";
    }


}
