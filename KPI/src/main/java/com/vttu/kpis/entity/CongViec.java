package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
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
    //int trangthaicongviec;
    float phantramhoanthanh;
    String macongvieccha;
    int ma_nguoitao;
    String ten_nguoitao;
    String mota;
    Date thoigiantao = new Date();
    boolean xacnhan = false;
    boolean yeucauxacnhan = false;
    boolean yeucaugiahan = false;
    boolean xacnhangiahan = false;
    boolean trehan = false;
    @ManyToOne
    @JoinColumn(name = "ma_nhom", nullable = false)
    NhomMucTieu nhomMucTieu;

    @ManyToOne
    @JoinColumn(name = "ma_trangthai", nullable = false)
    TrangThaiCongViec trangThaiCongViec;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_muctieu", nullable = false)
    MucTieu mucTieu;

    @ManyToOne
    @JoinColumn(name = "ma_danhcho", nullable = false)
    DanhCho danhCho;

    @ManyToOne
    @JoinColumn(name = "ma_ketqua", nullable = false)
    KetQuaCongViec ketQuaCongViec;

    @OneToMany(mappedBy = "congViec",cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<XacNhan> xacNhans;

    @OneToMany(mappedBy = "congViec",cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<PhanCongDonVi> phanCongDonVis;

    @OneToMany(mappedBy = "congViec", cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<PhanCongLanhDao> phanCongLanhDaos;

    @OneToMany(mappedBy = "congViec", cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<PhanCongNhanVien> phanCongNhanViens;

    @OneToMany(mappedBy = "congViec", cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<PhanCongBoPhan> phanCongBoPhans;

    @OneToMany(mappedBy = "congViec", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<ChuyenTiepCongViec> chuyenTiepCongViecs;

    @OneToMany(mappedBy = "congViec", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<GiaHan> giaHans;
}
