package com.vttu.kpis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Log_Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
     String recipient;
     String subject;
     String content;
     Date date = new Date();
     String status; // Ví dụ: "SUCCESS" hoặc "FAILED"

}
