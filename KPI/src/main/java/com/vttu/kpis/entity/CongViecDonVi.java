package com.vttu.kpis.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class CongViecDonVi {
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
    String mota;
    Date thoigiantao;
    @ManyToOne
    @JoinColumn(name = "ma_nhom", nullable = false)
    NhomMucTieu nhomMucTieu;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_muctieu", nullable = false)
    MucTieu mucTieu;

    @ManyToOne
    @JoinColumn(name = "ma_donvi", nullable = false)
    DonVi maDonVi;
}
