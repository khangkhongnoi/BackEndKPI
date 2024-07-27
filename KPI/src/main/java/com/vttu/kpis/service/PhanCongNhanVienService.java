package com.vttu.kpis.service;

import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.mapper.CongViecMapper;
import com.vttu.kpis.responsitory.CongViecResponsitory;
import com.vttu.kpis.responsitory.PhanCongNhanVienResponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhanCongNhanVienService {

    PhanCongNhanVienResponsitory phanCongNhanVienResponsitory;
    CongViecResponsitory congViecResponsitory;
    CongViecMapper congViecMapper;
    public List<CongViecResponse> getCongViecNhanVienNhanService(int manhanvien){

        return congViecResponsitory.getCongViecNhanVienNhan(manhanvien).stream()
                .map(congViec -> {
            CongViecResponse response = congViecMapper.toCongViecResponse(congViec);
            response.setNgayhientai(LocalDate.now());
            return response;
        }).toList();
    }

}
