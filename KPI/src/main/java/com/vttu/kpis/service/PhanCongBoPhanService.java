package com.vttu.kpis.service;

import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.mapper.CongViecMapper;
import com.vttu.kpis.responsitory.CongViecResponsitory;
import com.vttu.kpis.responsitory.PhanCongBoPhanResponsitory;
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
public class PhanCongBoPhanService {

    PhanCongBoPhanResponsitory boPhanResponsitory;
    CongViecMapper congViecMapper;
    CongViecResponsitory congViecResponsitory;
    public List<CongViecResponse> getCongViecBoPhanNhanService(int mabophan){

        return congViecResponsitory.getCongViecBoPhanNhan(mabophan).stream().map(congViecMapper::toCongViecResponse).toList();
    }
}
