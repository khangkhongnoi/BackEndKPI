package com.vttu.kpis.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vttu.kpis.dto.request.*;
import com.vttu.kpis.entity.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CongViecResponse {
    String macongviec;
    String tencongviec;
    LocalDate ngaybatdau;
    LocalDate ngayketthucdukien;
    LocalDate ngayhientai;
    String macongvieccha;
    int trongso;
    float phantramhoanthanh;
    int ma_nguoitao;
    String ten_nguoitao;
    String mota;
    boolean xacnhan;
    boolean yeucauxacnhan;
    boolean yeucaugiahan ;
    boolean xacnhangiahan ;
    boolean trehan;
    DanhCho danhCho;
    MucTieu mucTieu;
    NhomMucTieu nhomMucTieu;
    TrangThaiCongViec trangThaiCongViec;
    Set<PhanCongDonVi> phanCongDonVis;
    Set<PhanCongLanhDao> phanCongLanhDaos;
    Set<ChuyenTiepCongViecResponse> chuyenTiepCongViecs;
    Set<PhanCongNhanVien> phanCongNhanViens;
    Set<PhanCongBoPhan> phanCongBoPhans;
    KetQuaCongViec ketQuaCongViec;
    Set<XacNhan> xacNhans;
   // Set<GiaHan> giaHans;

}
