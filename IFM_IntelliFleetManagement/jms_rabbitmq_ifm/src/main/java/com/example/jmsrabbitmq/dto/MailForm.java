package com.example.jmsrabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailForm {
    private String name;
    private String sender;
    private String recipient;
    private String subject;
    private String message;
}
