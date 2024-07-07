package com.vttu.kpis.dto.request;

import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.entity.Quyen;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhanCongLanhDaoRequest {
    Long maphanconglanhdao;
    CongViec congViec;
    NhanVien nhanVien;
    Quyen quyen;
}
