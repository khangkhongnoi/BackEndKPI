package com.vttu.kpis.dto.request;

import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.DonVi;
import com.vttu.kpis.entity.Quyen;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhanCongDonViRequest {

    Long maphancongdonvi;
    @NotBlank(message = "Công việc không được phép trống")
    CongViec congViec;
    @NotBlank(message = "Đơn vị không được phép trống")
    DonVi donVi;
    @NotBlank(message = "Quyền không được phép trống")
    Quyen quyen;
}
