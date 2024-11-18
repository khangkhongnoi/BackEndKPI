package com.vttu.kpis.controller;

import com.nimbusds.jose.shaded.gson.Gson;
import com.vttu.kpis.dto.request.EmailRequest;
import com.vttu.kpis.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;
    KafkaTemplate<String, String> kafkaTemplate;
    static final String TOPIC = "email_topic";
    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String toEmail, @RequestParam String subject, @RequestParam String body) {
        emailService.sendSimpleEmail(toEmail, subject, body);
        return "Email sent successfully";
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        String emailMessage = new Gson().toJson(emailRequest);
        kafkaTemplate.send(TOPIC, emailMessage);
        return "Email message sent to Kafka topic successfully!";
    }

}
