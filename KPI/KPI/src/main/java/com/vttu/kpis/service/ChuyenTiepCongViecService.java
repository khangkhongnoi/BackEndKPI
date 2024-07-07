package com.vttu.kpis.service;

import com.vttu.kpis.dto.request.ChuyenTiepCongViecRequest;
import com.vttu.kpis.dto.request.CongViecChuyenTiepResquest;
import com.vttu.kpis.dto.request.PhanCongLanhDaoRequest;
import com.vttu.kpis.dto.response.ChuyenTiepCongViecResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.dto.response.NhanVienResponse;
import com.vttu.kpis.entity.ChuyenTiepCongViec;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.PhanCongLanhDao;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.ChuyenTiepCongViecMapper;
import com.vttu.kpis.mapper.CongViecChuyenTiepMapper;
import com.vttu.kpis.mapper.CongViecMapper;
import com.vttu.kpis.mapper.NhanVienMapper;
import com.vttu.kpis.responsitory.ChuyenTiepCongViecResponsitory;
import com.vttu.kpis.responsitory.CongViecResponsitory;
import com.vttu.kpis.responsitory.NhanVienResponsitory;
import com.vttu.kpis.responsitory.QuyenResponsitory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChuyenTiepCongViecService {

    NhanVienResponsitory nhanVienRepository;
    QuyenResponsitory quyenRepository;
    CongViecResponsitory congViecResponsitory;
    ChuyenTiepCongViecResponsitory chuyenTiepCongViecResponsitory;
    @Getter
    CongViecChuyenTiepMapper congViecChuyenTiepMapper;
    ChuyenTiepCongViecMapper chuyenTiepCongViecMapper;
    CongViecMapper congViecMapper;
    NhanVienMapper nhanVienMapper;

    @Transactional(rollbackFor = { Exception.class })
    public ChuyenTiepCongViecResponse createCongViecChuyenTiep(String macongviec,ChuyenTiepCongViecRequest request){

        // kiểm tra sự tồn tại của công việc
        CongViec cong = congViecResponsitory.findByMacongviec(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));


        if(request == null)
            throw  new AppException(ErrorCode.PhanCongNhanVienIsEmpty);

        Set<ChuyenTiepCongViec> chuyenTiepCongViecs = new HashSet<>();
        // Xử lý phân công lãnh đạo từ request
        for (CongViecChuyenTiepResquest list : request.getCongViecChuyenTieps()) {
            ChuyenTiepCongViec chuyenTiepCongViec = new ChuyenTiepCongViec();
            chuyenTiepCongViec.setCongViec(cong);
            chuyenTiepCongViec.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                    .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
            chuyenTiepCongViec.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                    .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
            chuyenTiepCongViecs.add(chuyenTiepCongViec);

            chuyenTiepCongViecResponsitory.save(chuyenTiepCongViec);
        }
        ChuyenTiepCongViecResponse chuyen = new ChuyenTiepCongViecResponse();

        return  chuyen;
    }

    public List<NhanVienResponse> getChuyenTiepByMaCongViec (String macongviec){

        return nhanVienRepository.getNhanVienDuocNhanChuyenTiepCongViecByMaCongViec(macongviec).stream().map(nhanVienMapper::toNhanVienResponse).toList();
    }

    public List<CongViecResponse> getCongViecChuyenTiepByMaNhanVien(int manhanvien){

        return congViecResponsitory.getCongViecChuyenTiepByMaNhanVien(manhanvien).stream().map(congViecMapper::toCongViecResponse).toList();
    }
}
