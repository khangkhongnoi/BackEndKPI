package com.vttu.kpis.service.nhanviec;

import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.CongViecMapper;
import com.vttu.kpis.mapper.PhanCongDonViMapper;
import com.vttu.kpis.responsitory.CongViecResponsitory;
import com.vttu.kpis.responsitory.DonViResponsitory;
import com.vttu.kpis.responsitory.PhanCongDonViRespository;
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
public class NhanViecDonViServer {

    PhanCongDonViRespository phanCongDonViRespository;
    DonViResponsitory donViResponsitory;
    PhanCongDonViMapper phanCongDonViMapper;
    CongViecResponsitory congViecResponsitory;
    CongViecMapper congViecMapper;

    public List<CongViecResponse> getCongViecNhanTheoDonVi(int madonvi){
        donViResponsitory.findById(madonvi)
                .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED));
        return congViecResponsitory.getCongViecDonVi(madonvi).stream().map(congViecMapper::toCongViecResponse).toList();
    }

}
