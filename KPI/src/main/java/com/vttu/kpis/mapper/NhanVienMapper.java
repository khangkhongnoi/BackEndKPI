package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.response.NhanVienResponse;

import com.vttu.kpis.entity.NhanVien;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface NhanVienMapper {

    NhanVienResponse toNhanVienResponse(NhanVien nhanVien);
    NhanVien toNhanVien(NhanVienResponse nhanVienResponse);
}
