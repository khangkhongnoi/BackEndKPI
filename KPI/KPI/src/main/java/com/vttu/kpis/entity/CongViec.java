package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class CongViec {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String macongviec;
    String tencongviec;
    LocalDate ngaybatdau;
    LocalDate ngayketthucdukien;
    int trongso;
    int trangthaicongviec;
    float phantramhoanthanh;
    String macongvieccha;
    int ma_nguoitao;
    String ten_nguoitao;

    @ManyToOne
    @JoinColumn(name = "ma_nhom", nullable = false)
    NhomMucTieu nhomMucTieu;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_muctieu", nullable = false)
    MucTieu mucTieu;

    @ManyToOne
    @JoinColumn(name = "ma_danhcho", nullable = false)
    DanhCho danhCho;

    @OneToMany(mappedBy = "congViec",cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<PhanCongDonVi> phanCongDonVis;

    @OneToMany(mappedBy = "congViec", cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<PhanCongLanhDao> phanCongLanhDaos;

    @OneToMany(mappedBy = "congViec", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<ChuyenTiepCongViec> chuyenTiepCongViecs;
}
