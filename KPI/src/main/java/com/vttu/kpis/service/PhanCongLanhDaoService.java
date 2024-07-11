package com.vttu.kpis.service;

import com.vttu.kpis.dto.request.ChuyenTiepCongViecRequest;
import com.vttu.kpis.dto.request.PhanCongLanhDaoRequest;
import com.vttu.kpis.dto.response.ChuyenTiepCongViecResponse;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.entity.PhanCongLanhDao;
import com.vttu.kpis.entity.Quyen;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.ChuyenTiepCongViecMapper;
import com.vttu.kpis.mapper.PhanCongLanhDaoMapper;
import com.vttu.kpis.responsitory.CongViecResponsitory;
import com.vttu.kpis.responsitory.NhanVienResponsitory;
import com.vttu.kpis.responsitory.PhanCongLanhDaoRepository;
import com.vttu.kpis.responsitory.QuyenResponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PhanCongLanhDaoService {

    PhanCongLanhDaoRepository phanCongLanhDaoRepository;
    ChuyenTiepCongViecMapper chuyenTiepCongViecMapper;
    NhanVienResponsitory nhanVienRepository;
    QuyenResponsitory quyenRepository;
    CongViecResponsitory congViecResponsitory;

}
