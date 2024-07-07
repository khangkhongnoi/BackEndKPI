package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int manhanvien;
    String tennhanvien;
    String email;

    @OneToOne(mappedBy = "nhanVien")
    @JsonIgnore
    TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "nhanVien")
    @JsonIgnore
    Set<PhanCongLanhDao> phanCongLanhDaos;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ma_donvi", nullable = false)
    DonVi donVi;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ma_bophan", nullable = true)
    BoPhan boPhan;

    @OneToMany(mappedBy = "nhanVien")
    @JsonIgnore
     Set<ChuyenTiepCongViec> chuyenTiepCongViecs;

    @OneToMany(mappedBy = "nhanVien")
    Set<NhanVien_ChucVu> nhanVienChucVus;
}
