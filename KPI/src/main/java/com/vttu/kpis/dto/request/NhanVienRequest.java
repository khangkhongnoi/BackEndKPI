package com.vttu.kpis.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vttu.kpis.entity.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhanVienRequest {

    int manhanvien;
    String tennhanvien;
    String email;
    TaiKhoan taiKhoan;


    Set<PhanCongLanhDao> phanCongLanhDaos;
    DonVi donVi;
    BoPhan boPhan;

    Set<ChucVu> chucVus;
}
