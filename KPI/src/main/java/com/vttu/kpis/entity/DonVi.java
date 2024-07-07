package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class DonVi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int madonvi;
    String tendonvi;

    @OneToMany(mappedBy = "donVi",fetch = FetchType.LAZY)
    @JsonIgnore
    Set<BoPhan> boPhans;

    @OneToMany(mappedBy = "donVi" , fetch = FetchType.LAZY)
    @JsonIgnore
    Set<NhanVien> nhanViens;

    @OneToMany(mappedBy = "donVi", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<PhanCongDonVi> phanCongDonVis;

    @OneToMany(mappedBy = "donVi")
    @JsonIgnore
    Set<NhanVien_ChucVu> nhanVienChucVus;

}
