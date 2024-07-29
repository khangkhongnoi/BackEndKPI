package com.vttu.kpis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Log_GhiLạiTrangThaiCongViec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    int trangthai;
    LocalDateTime thoigian;
    String macongviec;
}
