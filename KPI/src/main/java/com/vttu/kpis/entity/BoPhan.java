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
public class BoPhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int mabophan;
    String tenbophan;

    @ManyToOne
    @JoinColumn(name = "ma_donvi", nullable = false)
    DonVi donVi;

    @OneToMany(mappedBy = "boPhan" ,fetch = FetchType.LAZY)
    @JsonIgnore
    Set<NhanVien> nhanViens;

    @OneToMany(mappedBy = "boPhan", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<PhanCongBoPhan > phanCongBoPhans;

    @OneToMany(mappedBy = "boPhan")
    @JsonIgnore
    Set<NhanVien_ChucVu> nhanVienChucVus;

}
