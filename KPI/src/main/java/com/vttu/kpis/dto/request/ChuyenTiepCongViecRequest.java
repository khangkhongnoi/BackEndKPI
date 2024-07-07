package com.vttu.kpis.dto.request;

import com.vttu.kpis.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChuyenTiepCongViecRequest {


    @NotNull(message = "Công việc không được phép trống")
        Set<CongViecChuyenTiepResquest> congViecChuyenTieps;
}
