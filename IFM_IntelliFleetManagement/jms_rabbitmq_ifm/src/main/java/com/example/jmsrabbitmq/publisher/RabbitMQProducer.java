package com.example.jmsrabbitmq.publisher;

import com.example.jmsrabbitmq.dto.ContactUsForm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public void sendEmailToManagement(ContactUsForm contactUsForm) throws MessagingException {
        String from = contactUsForm.getEmail();
        String to = "sargad123@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("New IFM's Contact Us Form");
        helper.setFrom(from);
        helper.setTo(to);

        String content = String.format("""
                        <b>The Request is from %s</b>
                        <br>Subject: %s</br>
                        <b>%s</b>
                        <br>Best Regards, %s</br>
                        """ /*+
                        "<img src='cid:image001'/>"*/
                , contactUsForm.getEmail(), contactUsForm.getSubject(), contactUsForm.getMessage(), contactUsForm.getName());
        helper.setText(content, true);

        //FileSystemResource resource = new FileSystemResource(new File("picture.png"));
        //helper.addInline("image001", resource);

        mailSender.send(message);
    }

    public void sendEmailToClient(ContactUsForm contactUsForm) throws MessagingException {
        String from = "ifm@gmail.com";
        String to = contactUsForm.getEmail();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("IFM's thank u for your request");
        helper.setFrom(from);
        helper.setTo(to);

        String content = String.format("""
                        <b>Dear %s,</b>
                        <br>thank u for your request!</br>
                        <b>we got your request about %s. We will contact u soon.</b>
                        <br>Best Regards, IFM's management</br>
                        """ /*+
                        "<img src='cid:image001'/>"*/
                , contactUsForm.getName(), contactUsForm.getSubject());
        helper.setText(content, true);

        //FileSystemResource resource = new FileSystemResource(new File("picture.png"));
        //helper.addInline("image001", resource);

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
