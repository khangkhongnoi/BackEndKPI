package com.vttu.kpis.service;

import com.vttu.kpis.dto.request.BoPhanRequest;
import com.vttu.kpis.dto.request.DonViRequest;
import com.vttu.kpis.dto.response.BoPhanResponse;
import com.vttu.kpis.dto.response.DonViResponse;
import com.vttu.kpis.entity.BoPhan;
import com.vttu.kpis.entity.DonVi;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
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

public List<BoPhanResponse> getAllBoPhan(){
    return boPhanResponsitory.findAll().stream().map(boPhanMapper::toBophanResponse).toList();
}

    public BoPhanResponse createBoPhan(BoPhanRequest boPhanRequest){

        if(boPhanResponsitory.existsByTenbophan(boPhanRequest.getTenbophan()))
            throw new AppException(ErrorCode.TenDonVi_EXISTED);
        BoPhan boPhan = new BoPhan();
        boPhan.setTenbophan(boPhanRequest.getTenbophan());
        boPhan.setMota(boPhanRequest.getMota());

        DonVi donVi = new DonVi();
        donVi.setMadonvi(boPhanRequest.getDonVi().getMadonvi());
        boPhan.setDonVi(donVi);

        return boPhanMapper.toBophanResponse(boPhanResponsitory.save(boPhan));
    }
}
