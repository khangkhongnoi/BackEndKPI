package com.vttu.kpis.controller;

import com.vttu.kpis.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String toEmail, @RequestParam String subject, @RequestParam String body) {
        emailService.sendSimpleEmail(toEmail, subject, body);
        return "Email sent successfully";
    }
}
