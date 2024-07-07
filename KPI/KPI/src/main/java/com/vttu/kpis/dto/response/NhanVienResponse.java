package com.vttu.kpis.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vttu.kpis.entity.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhanVienResponse {

    int manhanvien;
    String tennhanvien;
    String email;
    TaiKhoan taiKhoan;
//    Set<PhanCongLanhDao> phanCongLanhDaos;
    DonViResponse donVi;
    BoPhanResponse boPhan;
    Set<ChucVu> chucVus;
}

