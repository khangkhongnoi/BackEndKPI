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
public class Log_TrangThaiCongViec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    int trangthai;
    Date thoigian = new Date();
    @NotNull(message = "Mã nhân viên không được phép trống")
    int ma_nhanvien;
    @NotNull(message = "Mã công việc không được phép trống")
    String ma_congviec;
}
