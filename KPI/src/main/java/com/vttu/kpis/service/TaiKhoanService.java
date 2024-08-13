package com.vttu.kpis.service;

import com.vttu.kpis.dto.request.LoginRequest;
import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.entity.TaiKhoan;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.responsitory.TaiKhoanRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TaiKhoanService {

    TaiKhoanRepository taiKhoanRepository;

    public TaiKhoan getbyid (String mataikhoan){

        return taiKhoanRepository.findByTentaikhoan(mataikhoan).orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
    }

    public boolean checkDangNhap(LoginRequest request){
            TaiKhoan taiKhoan = taiKhoanRepository.kiemtradangnhap(request.getUsername());

            if(Objects.equals(taiKhoan.getTentaikhoan(), request.getUsername()) || taiKhoan.getMatkhau().equals(request.getPassword())){
                return true;
            }
            else
                return false;


    }

    public List<Map<String,Object>> listtaikhoanServer() {
        return taiKhoanRepository.listtaikhoan();
    }

}
