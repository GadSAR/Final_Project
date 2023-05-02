package com.example.jmsrabbitmq.controller;

import com.example.jmsrabbitmq.dto.ContactUsForm;
import com.example.jmsrabbitmq.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final RabbitMQProducer rabbitMQProducer;

    public MessageController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "Welcome to IFM's jms rabbitmq";
    }

    @PostMapping("/contactUs")
    public ResponseEntity<String> publishContactUs(@RequestBody ContactUsForm form) {
        List<String> list = List.of(form.getName(), form.getEmail(), form.getSubject(), form.getMessage());
        rabbitMQProducer.send(list);
        return ResponseEntity.ok("Form published successfully");
    }
}
