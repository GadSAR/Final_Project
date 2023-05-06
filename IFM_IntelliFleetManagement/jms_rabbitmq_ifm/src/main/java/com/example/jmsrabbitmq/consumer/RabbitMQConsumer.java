package com.example.jmsrabbitmq.consumer;

import com.example.jmsrabbitmq.dto.ContactUsForm;
import com.example.jmsrabbitmq.dto.MailForm;
import com.example.jmsrabbitmq.publisher.RabbitMQProducer;
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

    @RabbitListener(queues = "${rabbitmq.contact-us-to-management.queue.name}")
    public void contactUsToManagement(List<String> form) {
        ContactUsForm contactUsForm = new ContactUsForm(form.get(0), form.get(1), form.get(2), form.get(3));
        try {
            rabbitMQProducer.sendEmailToManagement(contactUsForm);
            logger.info("Received Message From RabbitMQ! Mail sent to IFM's management");
        } catch (Exception e) {
            try {
                Thread.sleep(5000); // sleep for 5 seconds
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            rabbitMQProducer.send_contactUsToManagement(form);
        }
    }

    @RabbitListener(queues = "${rabbitmq.contact-us-to-client.queue.name}")
    public void contactUsToClient(List<String> form) {
        ContactUsForm contactUsForm = new ContactUsForm(form.get(0), form.get(1), form.get(2), form.get(3));
        try {
            rabbitMQProducer.sendEmailToClient(contactUsForm);
            logger.info("Received Message From RabbitMQ! Auto reply sent back to: " + form.get(1));
        } catch (Exception e) {
            try {
                Thread.sleep(5000); // sleep for 5 seconds
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            rabbitMQProducer.send_contactUsToClient(form);
        }
    }

    @RabbitListener(queues = "${rabbitmq.user-to-admin.queue.name}")
    public void userToAdmin(List<String> form) {
        MailForm mailForm = new MailForm(form.get(0), form.get(1), form.get(2), form.get(3), form.get(4));
        try {
            rabbitMQProducer.sendEmail(mailForm);
            logger.info("Received Message From RabbitMQ! Mail sent to Admin");
        } catch (Exception e) {
            try {
                Thread.sleep(5000); // sleep for 5 seconds
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            rabbitMQProducer.send_userToAdmin(form);
        }
    }

    @RabbitListener(queues = "${rabbitmq.admin-to-user.queue.name}")
    public void adminToUser(List<String> form) {
        MailForm mailForm = new MailForm(form.get(0), form.get(1), form.get(1), form.get(2), form.get(3));
        try {
            rabbitMQProducer.sendEmail(mailForm);
            logger.info("Received Message From RabbitMQ! Mail sent to User");
        } catch (Exception e) {
            try {
                Thread.sleep(5000); // sleep for 5 seconds
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            rabbitMQProducer.send_adminToUser(form);
        }
    }
}
