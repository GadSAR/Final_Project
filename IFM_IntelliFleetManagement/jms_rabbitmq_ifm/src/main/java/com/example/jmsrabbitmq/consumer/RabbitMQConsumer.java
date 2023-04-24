package com.example.jmsrabbitmq.consumer;

import com.example.jmsrabbitmq.dto.ContactUsForm;
import com.example.jmsrabbitmq.publisher.RabbitMQProducer;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitMQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final RabbitMQProducer rabbitMQProducer;

    public RabbitMQConsumer(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receivedForm(List<String> form) throws MessagingException {
        ContactUsForm contactUsForm = new ContactUsForm(form.get(0), form.get(1), form.get(2), form.get(3));
        rabbitMQProducer.sendEmailToManagement(contactUsForm);
        logger.info("Received Message From RabbitMQ! Mail sent to IFM's management");
        rabbitMQProducer.sendEmailToClient(contactUsForm);
        logger.info("Auto reply sent back to: " + form.get(1));

    }
}
