package com.vttu.kpis.service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.vttu.kpis.dto.request.EmailRequest;
import com.vttu.kpis.entity.Log_Email;
import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.responsitory.NhanVienResponsitory;
import com.vttu.kpis.responsitory.log.Log_EmailResponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailService {
     JavaMailSender mailSender;
     NhanVienResponsitory nhanVienResponsitory;
     KafkaTemplate<String, String> kafkaTemplate;
     Log_EmailResponsitory log_EmailResponsitory;
     final String TOPIC = "email_topic";

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public  boolean sendMail(int manguoitao, String subject, String content) {
        NhanVien nhanVien = nhanVienResponsitory.findById(manguoitao)
                .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED));
        EmailRequest emailRequest = new EmailRequest();

        emailRequest.setTo(nhanVien.getEmail());
        emailRequest.setSubject(subject);
        emailRequest.setText(content);
        String emailMessage = new Gson().toJson(emailRequest);

        try
        {
            kafkaTemplate.send(TOPIC, emailMessage);

            // Tạo và lưu thông tin EmailLog vào cơ sở dữ liệu sau khi gửi thành công
            Log_Email emailLog = new Log_Email();
            emailLog.setRecipient(nhanVien.getEmail());
            emailLog.setSubject(subject);
            emailLog.setContent(content);
            emailLog.setStatus("SUCCESS");
            log_EmailResponsitory.save(emailLog);
            return true;

        } catch (Exception e) {
            Log_Email emailLog = new Log_Email();
            emailLog.setRecipient(nhanVien.getEmail());
            emailLog.setSubject(subject);
            emailLog.setContent(content);
            emailLog.setStatus("FAILED");
            log_EmailResponsitory.save(emailLog);
            return false;
        }
    }

}
