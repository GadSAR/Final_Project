package com.example.jmsrabbitmq.controller;

import com.example.jmsrabbitmq.dto.ContactUsForm;
import com.example.jmsrabbitmq.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final RabbitMQProducer rabbitMQProducer;

    public MessageController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam("message") String message) {
        rabbitMQProducer.send(message);
        return ResponseEntity.ok("Message published successfully");
    }

    @PostMapping("/contact-us")
    public ResponseEntity<String> publishContactUs(@RequestBody ContactUsForm form) {
        List<String> list = List.of(form.getName(), form.getEmail(), form.getSubject(), form.getMessage());
        rabbitMQProducer.send(list);
        return ResponseEntity.ok("Form published successfully");
    }
}
