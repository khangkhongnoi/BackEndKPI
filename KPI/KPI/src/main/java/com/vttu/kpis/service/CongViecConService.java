package com.vttu.kpis.service;

import com.vttu.kpis.dto.request.CongViecConNhanVienRequest;
import com.vttu.kpis.dto.request.PhanCongDonViRequest;
import com.vttu.kpis.dto.request.PhanCongLanhDaoRequest;
import com.vttu.kpis.dto.response.CongViecConNhanVienResponse;
import com.vttu.kpis.entity.*;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.CongViecConNhanVienMapper;
import com.vttu.kpis.mapper.CongViecMapper;
import com.vttu.kpis.responsitory.*;
import jakarta.validation.constraints.Null;
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
public class CongViecConService {

    CongViecResponsitory congViecResponsitory;
    CongViecConNhanVienMapper congViecConNhanVienMapper;
    DonViResponsitory donViRepository;
    MucTieuReoisitory mucTieuRepository;
    QuyenResponsitory quyenRepository;
    NhanVienResponsitory nhanVienRepository;
    NhomMucTieuResponsitory nhomMucTieuResponsitory;
    DanhChoResponsitory danhChoResponsitory;
    public CongViecConNhanVienResponse createCongViec(CongViecConNhanVienRequest request){



        CongViecConNhanVienResponse congViecConNhanVienResponse = new CongViecConNhanVienResponse();

      // kiểm tra sự tồn tại của công việc
        CongViec cong = congViecResponsitory.findByMacongviec(request.getMacongvieccha())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

        // Tạo đối tượng CongViec từ request
        CongViec congViec = congViecConNhanVienMapper.toCreateCongViec(request);
                congViec.setTrangthaicongviec(1);
                congViec.setPhantramhoanthanh(0);
        // Lấy MucTieu từ repository dựa trên mã mục tiêu từ request
        MucTieu mucTieu = mucTieuRepository.findById(cong.getMucTieu().getMamuctieu())
                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));

       danhChoResponsitory.findById(request.getDanhCho().getMadanhcho())
                .orElseThrow(() -> new AppException(ErrorCode.DanhCho_NOT_EXISTED));

        NhomMucTieu nhomMucTieu = nhomMucTieuResponsitory.findById(cong.getNhomMucTieu().getManhom())
                .orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED));

        // Khởi tạo tập hợp phân công đơn vị và phân công lãnh đạo
        // phanconglanhdao đóng vai trò là nhân viên thực hiện công việc được giao

        if(request.getDanhCho().getMadanhcho() == 1) {

            if(request.getPhanCongLanhDaos().isEmpty())
              throw  new AppException(ErrorCode.PhanCongNhanVienIsEmpty);

            Set<PhanCongLanhDao> phanCongLanhDaos = new HashSet<>();
            // Xử lý phân công lãnh đạo từ request
            for (PhanCongLanhDaoRequest list : request.getPhanCongLanhDaos()) {
                PhanCongLanhDao phanCongLanhDao = new PhanCongLanhDao();
                phanCongLanhDao.setCongViec(congViec);
                phanCongLanhDao.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                        .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
                phanCongLanhDao.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                        .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                phanCongLanhDaos.add(phanCongLanhDao);
            }
            congViec.setPhanCongLanhDaos(phanCongLanhDaos);
        }
        else if(request.getDanhCho().getMadanhcho() == 2){

            if(request.getPhanCongDonVis().isEmpty())
                throw new AppException(ErrorCode.PhanCongDonViIsEmpty);

            Set<PhanCongDonVi> phanCongDonVis = new HashSet<>();
            for (PhanCongDonViRequest list : request.getPhanCongDonVis()) {
                PhanCongDonVi phanCongDonVi = new PhanCongDonVi();
                phanCongDonVi.setCongViec(congViec);
                phanCongDonVi.setDonVi(donViRepository.findById(list.getDonVi().getMadonvi())
                        .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED)));
                phanCongDonVi.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                        .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                phanCongDonVis.add(phanCongDonVi);
            }
            congViec.setPhanCongDonVis(phanCongDonVis);
        }
        // Gán MucTieu, PhanCongDonVi và PhanCongLanhDao cho CongViec
        congViec.setMucTieu(mucTieu);
        congViec.setNhomMucTieu(nhomMucTieu);


        // Lưu CongViec và trả về CongViecResponse
        return congViecConNhanVienMapper.toCongViecConNhanVienResponse(congViecResponsitory.save(congViec));

    }
}
