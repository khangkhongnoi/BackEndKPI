package com.vttu.kpis.service;

import com.vttu.kpis.dto.response.BoPhanResponse;
import com.vttu.kpis.mapper.BoPhanMapper;
import com.vttu.kpis.responsitory.BoPhanResponsitory;
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
public class BoPhanService {

    BoPhanResponsitory boPhanResponsitory;
    BoPhanMapper boPhanMapper;


public List<BoPhanResponse> getBoPhanByMaDonVi (int madonvi){

    return boPhanResponsitory.findBoPhanByMadonvi(madonvi).stream().map(boPhanMapper::toBophanResponse).toList();
}

}
