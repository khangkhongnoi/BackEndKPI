package com.vttu.kpis.service;

import com.vttu.kpis.dto.DonViDTO;
import com.vttu.kpis.dto.request.DonViRequest;
import com.vttu.kpis.dto.response.DonViResponse;
import com.vttu.kpis.entity.DonVi;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
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
    public List<DonViDTO> getAllDonVi(){
        return donViResponsitory.findDonViDTO();
    }

    public List<DonViResponse> getDonViPhoiHopThucHien(int madonvi){
        return donViResponsitory.getDonViPhoiHopThucHien(madonvi).stream().map(donViMapper::toDonViResponse).toList();
    }

    public DonViResponse createDonVi(DonViRequest donViRequest){

        if(donViResponsitory.existsByTendonvi(donViRequest.getTendonvi()))
            throw new AppException(ErrorCode.TenDonVi_EXISTED);
        DonVi donVi = new DonVi();
        donVi.setTendonvi(donViRequest.getTendonvi());
        donVi.setMota(donViRequest.getMota());

        return donViMapper.toDonViResponse(donViResponsitory.save(donVi));
    }

    public DonViDTO GetDonVi(int donViId){
        return donViResponsitory.findDonViDTOId(donViId);
    }

    public DonViResponse updateDonVi(DonViRequest donViRequest){

        DonVi donVi =  donViResponsitory.findById(donViRequest.getMadonvi())
                .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED));
        if(donViResponsitory.existsByTendonviAndMadonviNot(donViRequest.getTendonvi(),donViRequest.getMadonvi()))
            throw new AppException(ErrorCode.TenDonVi_EXISTED);
        donVi.setTendonvi(donViRequest.getTendonvi());
        donVi.setMota(donViRequest.getMota());

        return donViMapper.toDonViResponse(donViResponsitory.save(donVi));
    }
}
