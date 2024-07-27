package com.vttu.kpis.dto.request;


import com.vttu.kpis.entity.CongViec;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiaHanRequest {

    long magiahan;
    LocalDate thoigian;
    String lydo;
    long magiahancha;
    @NotNull(message = "Mã công việc không được phép trống")
    CongViec congViec;
}
