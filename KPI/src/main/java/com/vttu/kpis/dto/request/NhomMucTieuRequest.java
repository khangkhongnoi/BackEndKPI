package com.vttu.kpis.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhomMucTieuRequest {
    int manhom;

    @NotBlank(message = "Tên Nhóm không được phép trống")
    String tennhom;
    @NotBlank(message = "Mã màu không được phép trống")
    String mamau;
}
