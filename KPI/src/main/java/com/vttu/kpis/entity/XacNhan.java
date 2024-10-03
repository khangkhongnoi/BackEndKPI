package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class XacNhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long maxacnhan;
    String noidung;
    boolean trangthai;
    LocalDateTime thoigian = LocalDateTime.now();
    long maxacnhancha;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ma_congviec", nullable = false)
    CongViec congViec;
}
