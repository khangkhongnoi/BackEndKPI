package com.vttu.kpis.service;

import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.dto.response.PhanCongDonViResponse;
import com.vttu.kpis.entity.DonVi;
import com.vttu.kpis.entity.PhanCongDonVi;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.CongViecMapper;
import com.vttu.kpis.mapper.PhanCongDonViMapper;
import com.vttu.kpis.responsitory.CongViecResponsitory;
import com.vttu.kpis.responsitory.DonViResponsitory;
import com.vttu.kpis.responsitory.NhanVienResponsitory;
import com.vttu.kpis.responsitory.PhanCongDonViRespository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PhanCongDonViService {
    PhanCongDonViRespository phanCongDonViRespository;
    DonViResponsitory donViResponsitory;
    PhanCongDonViMapper phanCongDonViMapper;
    CongViecResponsitory congViecResponsitory;
    CongViecMapper congViecMapper;
    NhanVienResponsitory nhanVienResponsitory;
    public List<PhanCongDonViResponse> listPhanConDonVi (int madonvi){

        return phanCongDonViRespository.getDonVi(madonvi).stream().map(phanCongDonViMapper::toPhanCongDonViResponse).toList();
    }

    public List<CongViecResponse> getCongViecTheoDonVi(int madonvi, int manhanvien){
        donViResponsitory.findById(madonvi)
                .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED));
        nhanVienResponsitory.findById(manhanvien)
                .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_FOUND));
        return congViecResponsitory.getCongViecDonVi(madonvi,manhanvien).stream().map(congViecMapper::toCongViecResponse).toList();
    }


    public List<CongViecResponse> getCongViecByMaNguoiTao (int ma_nguoitao){

        return congViecResponsitory.findByMa_nguoitao(ma_nguoitao).stream().map(congViecMapper::toCongViecResponse).toList();

    }
}
