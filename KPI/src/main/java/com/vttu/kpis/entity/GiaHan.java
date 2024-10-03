package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class GiaHan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long magiahan;
    LocalDate thoigian;
    LocalDateTime thoigiantao = LocalDateTime.now();
    String lydo;
    boolean trangthai;
    String nguoitao;
    long magiahancha;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "ma_congviec", nullable = false)
    @NotNull(message = "Công việc không được phép trống")
    CongViec congViec;

}
