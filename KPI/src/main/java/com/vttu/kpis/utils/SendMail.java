package com.vttu.kpis.utils;

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
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SendMail {

    static NhanVienResponsitory nhanVienRepository;
    static Log_EmailResponsitory logEmailResponsitory;
    static KafkaTemplate<String, String> kafkaTemplate;
    static final String TOPIC = "email_topic";

    public static boolean sendMail(int manguoitao, String subject, String content) {
        NhanVien nhanVien = new NhanVien();
        nhanVien = nhanVienRepository.findById(manguoitao)
                .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED));
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(nhanVien.getEmail());
        emailRequest.setSubject(subject);
        emailRequest.setText(content);
        String emailMessage = new Gson().toJson(emailRequest);


        kafkaTemplate.send(TOPIC, emailMessage);

        return true;
    }
}
