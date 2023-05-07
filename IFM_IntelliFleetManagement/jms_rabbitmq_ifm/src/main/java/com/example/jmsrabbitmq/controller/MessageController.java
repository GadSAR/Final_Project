package com.example.jmsrabbitmq.controller;

import com.example.jmsrabbitmq.dto.ContactUsForm;
import com.example.jmsrabbitmq.dto.MailForm;
import com.example.jmsrabbitmq.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class MessageController {

    private final RabbitMQProducer rabbitMQProducer;

    public MessageController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @PostMapping("/contactUs")
    public ResponseEntity<String> publishContactUs(@RequestBody ContactUsForm form) {
        List<String> list = List.of(form.getName(), form.getEmail(), form.getSubject(), form.getMessage());
        rabbitMQProducer.send_contactUsToClient(list);
        rabbitMQProducer.send_contactUsToManagement(list);
        return ResponseEntity.ok("contactUs published successfully");
    }

    @PostMapping("/mailToUser")
    public ResponseEntity<String> publishMailToUser(@RequestBody MailForm form) {
        List<String> list = List.of(form.getName(), form.getSender(), form.getRecipient(), form.getSubject(), form.getMessage());
        rabbitMQProducer.send_adminToUser(list);
        return ResponseEntity.ok("mailToUser published successfully");
    }

    @PostMapping("/mailToAdmin")
    public ResponseEntity<String> publishMailToAdmin(@RequestBody MailForm form) {
        List<String> list = List.of(form.getName(), form.getSender(), form.getRecipient(), form.getSubject(), form.getMessage());
        rabbitMQProducer.send_userToAdmin(list);
        return ResponseEntity.ok("mailToAdmin published successfully");
    }
}
