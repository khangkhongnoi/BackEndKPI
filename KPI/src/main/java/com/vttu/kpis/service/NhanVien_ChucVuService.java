package com.vttu.kpis.service;

import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.entity.NhanVien_ChucVu;
import com.vttu.kpis.entity.TaiKhoan;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.responsitory.NhanVienResponsitory;
import com.vttu.kpis.responsitory.NhanVien_ChucVuReponsitory;
import com.vttu.kpis.responsitory.TaiKhoanRepository;
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
public class NhanVien_ChucVuService {

    NhanVien_ChucVuReponsitory nhanVienChucVuReponsitory;
   TaiKhoanRepository taiKhoanRepository;

    public List<NhanVien_ChucVu> getAllNhanVienChucVu(String username){

        TaiKhoan taiKhoan = taiKhoanRepository.kiemtradangnhap(username);
            if(taiKhoan == null)
                throw new AppException(ErrorCode.USER_NOT_EXISTED);

            return nhanVienChucVuReponsitory.findNhanVien_ChucVuByNhanVien(taiKhoan.getNhanVien().getManhanvien());

    }

}
