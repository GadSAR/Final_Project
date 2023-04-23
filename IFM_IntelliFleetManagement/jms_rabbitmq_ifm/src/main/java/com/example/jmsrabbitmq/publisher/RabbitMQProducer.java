package com.example.jmsrabbitmq.publisher;

import com.example.jmsrabbitmq.dto.ContactUsForm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

@Service
public class RabbitMQProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(ContactUsForm contactUsForm) throws MessagingException {
        String from = "sender@gmail.com";
        String to = "recipient@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(contactUsForm.getSubject());
        helper.setFrom(from);
        helper.setTo(to);

        String content = String.format("<b>Dear %s</b>,<br><i>%s:.</i>"
                + "<br><img src='cid:image001'/>" + "<br><b>Best Regards</b>"
                , contactUsForm.getName(), contactUsForm.getMessage());
        helper.setText(content, true);

        FileSystemResource resource = new FileSystemResource(new File("picture.png"));
        helper.addInline("image001", resource);

        mailSender.send(message);
    }

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {
        LOGGER.info("Sending message to RabbitMQ: " + message);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    public void send(List<String> form) {
        LOGGER.info("Sending message to RabbitMQ: " + form.get(1));
        rabbitTemplate.convertAndSend(exchangeName, routingKey, form);
    }
}
