package com.vttu.kpis.entity;

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
public class Log_ThietLapCachTinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Date log_date = new Date();
    int maketqua;
    float phantram;
    @NotNull(message = "Mã công việc không được phép trống")
    String ma_congviec;

    @NotNull(message = "Mã nhân viên không được phép trống")
    int ma_nhanvien;
}
