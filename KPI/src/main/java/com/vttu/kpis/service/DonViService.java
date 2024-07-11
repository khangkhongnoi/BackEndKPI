package com.vttu.kpis.service;

import com.vttu.kpis.dto.response.DonViResponse;
import com.vttu.kpis.entity.DonVi;
import com.vttu.kpis.mapper.DonViMapper;
import com.vttu.kpis.responsitory.DonViResponsitory;
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
public class DonViService {

    DonViResponsitory donViResponsitory;
    DonViMapper donViMapper;
    public List<DonViResponse> getAllDonVi(){
        return donViResponsitory.findAll().stream().map(donViMapper::toDonViResponse).toList();
    }

    public List<DonViResponse> getDonViPhoiHopThucHien(int madonvi){
        return donViResponsitory.getDonViPhoiHopThucHien(madonvi).stream().map(donViMapper::toDonViResponse).toList();
    }

}
