package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Log_GiaoViec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(length = 1000)
    String log_noidung;
    Date log_date = new Date();

    @NotNull(message = "Mã công việc không được phép trống")
    String ma_congviec;

    @NotNull(message = "Mã nhân viên không được phép trống")
    int ma_nhanvien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "ma_hanhdong", nullable = false)
    @NotNull(message = "Mã hành động không được phép trống")
    HanhDong hanhDong;
}
