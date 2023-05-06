package com.example.jmsrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.contact-us-to-management.queue.name}")
    private String cutm_queue;
    @Value("${rabbitmq.contact-us-to-management.exchange.name}")
    private String cutm_exchange;
    @Value("${rabbitmq.contact-us-to-management.routing.key}")
    private String cutm_routingKey;
    @Value("${rabbitmq.contact-us-to-client.queue.name}")
    private String cutc_queue;
    @Value("${rabbitmq.contact-us-to-client.exchange.name}")
    private String cutc_exchange;
    @Value("${rabbitmq.contact-us-to-client.routing.key}")
    private String cutc_routingKey;
    @Value("${rabbitmq.admin-to-user.queue.name}")
    private String atu_queue;
    @Value("${rabbitmq.admin-to-user.exchange.name}")
    private String atu_exchange;
    @Value("${rabbitmq.admin-to-user.routing.key}")
    private String atu_routingKey;
    @Value("${rabbitmq.user-to-admin.queue.name}")
    private String uta_queue;
    @Value("${rabbitmq.user-to-admin.exchange.name}")
    private String uta_exchange;
    @Value("${rabbitmq.user-to-admin.routing.key}")
    private String uta_routingKey;

    // queue
    @Bean
    public Queue cutm_queue() {
        return new Queue(cutm_queue);
    }
    @Bean
    public Queue cutc_queue() {
        return new Queue(cutc_queue);
    }
    @Bean
    public Queue atu_queue() {
        return new Queue(atu_queue);
    }
    @Bean
    public Queue uta_queue() {
        return new Queue(uta_queue);
    }

    // exchange
    @Bean
    public TopicExchange cutm_exchange() {
        return new TopicExchange(cutm_exchange);
    }
    @Bean
    public TopicExchange cutc_exchange() {
        return new TopicExchange(cutc_exchange);
    }
    @Bean
    public TopicExchange atu_exchange() {
        return new TopicExchange(atu_exchange);
    }
    @Bean
    public TopicExchange uta_exchange() {
        return new TopicExchange(uta_exchange);
    }

    // binding
    @Bean
    public Binding cutm_binding(Queue cutm_queue, TopicExchange cutm_exchange) {
        return BindingBuilder.bind(cutm_queue).to(cutm_exchange).with(cutm_routingKey);
    }
    @Bean
    public Binding cutc_binding(Queue cutc_queue, TopicExchange cutc_exchange) {
        return BindingBuilder.bind(cutc_queue).to(cutc_exchange).with(cutc_routingKey);
    }
    @Bean
    public Binding atu_binding(Queue atu_queue, TopicExchange atu_exchange) {
        return BindingBuilder.bind(atu_queue).to(atu_exchange).with(atu_routingKey);
    }
    @Bean
    public Binding uta_binding(Queue uta_queue, TopicExchange uta_exchange) {
        return BindingBuilder.bind(uta_queue).to(uta_exchange).with(uta_routingKey);
    }
}