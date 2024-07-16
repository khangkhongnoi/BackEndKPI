package com.vttu.kpis.service;

import com.vttu.kpis.entity.TrangThaiCongViec;
import com.vttu.kpis.responsitory.TrangThaiCongViecResponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrangThaiCongViecService {

    TrangThaiCongViecResponsitory trangThaiCongViecResponsitory;

    public List<TrangThaiCongViec> getAllService() {
        return trangThaiCongViecResponsitory.findAll();
    }
}
