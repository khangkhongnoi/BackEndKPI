package com.vttu.kpis.service;

import com.vttu.kpis.dto.response.NhanVienResponse;
import com.vttu.kpis.dto.response.ThongTinLoginResponse;
import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.NhanVienMapper;
import com.vttu.kpis.responsitory.DonViResponsitory;
import com.vttu.kpis.responsitory.NhanVienResponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NhanVienService {

    NhanVienResponsitory nhanVienResponsitory;
    NhanVienMapper nhanVienMapper;
    DonViResponsitory donViResponsitory;
    public ThongTinLoginResponse luuthongtinnhanvien(int manhanvien, int machucvi){

        return nhanVienResponsitory.findByManhanvienAndChucVuss(manhanvien,machucvi);
    }
    public NhanVien getNhanVien(int id){
       NhanVien nhanVien = nhanVienResponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED));
       return  nhanVien;
    }

    public List<NhanVienResponse> getBanLanhDao(int madonvi){

        return nhanVienResponsitory.getAllBanLanhDao(madonvi).stream().map(nhanVienMapper::toNhanVienResponse).toList();
    }

    public List<NhanVienResponse> getNhanVienDonVi(int madonvi){

        donViResponsitory.findById(madonvi)
                .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED));

        return nhanVienResponsitory.getNhanVienDonVi(madonvi).stream().map(nhanVienMapper::toNhanVienResponse).toList();
    }
}
