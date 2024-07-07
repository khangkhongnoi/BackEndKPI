package com.vttu.kpis.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vttu.kpis.dto.request.DanhChoResquest;
import com.vttu.kpis.dto.request.PhanCongDonViRequest;
import com.vttu.kpis.dto.request.PhanCongLanhDaoRequest;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CongViecConNhanVienResponse {
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

    DanhChoResquest danhCho;
    @JsonIgnore
    Set<PhanCongLanhDaoRequest> phanCongLanhDaos;
    @JsonIgnore
    Set<PhanCongDonViRequest> phanCongDonVis;
}
