package com.vttu.kpis.dto.request;

import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.DonVi;
import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.entity.Quyen;
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
public class CongViecChuyenTiepResquest {

    CongViec congViec;

    @NotNull(message = "Nhân viên không được phép trống")
    NhanVienRequest nhanVien;

    @NotNull(message = "Quyền không được phép trống")
    QuyenRequest quyen;
}
