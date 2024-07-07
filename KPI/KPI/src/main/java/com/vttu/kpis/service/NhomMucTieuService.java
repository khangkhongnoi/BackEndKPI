package com.vttu.kpis.service;


import com.vttu.kpis.dto.request.NhomMucTieuRequest;
import com.vttu.kpis.dto.response.NhomMucTieuResponse;
import com.vttu.kpis.entity.NhomMucTieu;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.NhomMucTieuMapper;
import com.vttu.kpis.responsitory.NhomMucTieuResponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NhomMucTieuService {

    NhomMucTieuResponsitory nhomMucTieuResponsitory;
    NhomMucTieuMapper nhomMucTieuMapper;

   // @PreAuthorize("hasAnyRole('ADMIN', 'ROLE_USER')")
    public NhomMucTieuResponse createNhomMucTieu(NhomMucTieuRequest request){

        var authentiaction = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentiaction.getName());
            if(nhomMucTieuResponsitory.existsByTennhom(request.getTennhom())) throw new AppException(ErrorCode.NhomMucTieu_EXISTED);

            NhomMucTieu nhomMucTieu = new NhomMucTieu();
            nhomMucTieu.setTennhom(request.getTennhom());
            nhomMucTieu.setMamau(request.getMamau());
            return  nhomMucTieuMapper.toNhomMucTieuResponse(nhomMucTieuResponsitory.save(nhomMucTieu));
    }

    public List<NhomMucTieuResponse> getNhomMucTieu(){

        return nhomMucTieuResponsitory.findAll().stream().map(nhomMucTieuMapper::toNhomMucTieuResponse).toList();
    }

    public NhomMucTieuResponse getNhom(int manhom){
        return nhomMucTieuMapper.toNhomMucTieuResponse(
                nhomMucTieuResponsitory.findById(manhom).orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED))
        );
    }

    public NhomMucTieuResponse updateNhom(int manhom, NhomMucTieuRequest request){
            NhomMucTieu nhomMucTieu = nhomMucTieuResponsitory.findById(manhom).orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED));
            request.setManhom(manhom);
            nhomMucTieuMapper.updateNhom(nhomMucTieu,request);
            return nhomMucTieuMapper.toNhomMucTieuResponse(nhomMucTieuResponsitory.save(nhomMucTieu));
    }


}
