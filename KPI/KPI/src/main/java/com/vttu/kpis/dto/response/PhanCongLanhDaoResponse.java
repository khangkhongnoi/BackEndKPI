package com.vttu.kpis.dto.response;

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
public class PhanCongLanhDaoResponse {
    Long maphanconglanhdao;
    CongViecResponse congViec;
    NhanVienResponse nhanVien;
    QuyenResponse quyen;
}
