package com.vttu.kpis.service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.vttu.kpis.dto.request.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEmailConsumer {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "email_topic", groupId ="email_group")
    public void consume(String message) {
        Gson gson = new Gson();
        EmailRequest emailRequest = gson.fromJson(message, EmailRequest.class);
        emailService.sendSimpleEmail(emailRequest.getTo(),emailRequest.getSubject(),emailRequest.getText());
    }
}
