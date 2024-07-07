package com.vttu.kpis.service;

import com.vttu.kpis.dto.request.CongViecRequest;
import com.vttu.kpis.dto.request.PhanCongDonViRequest;
import com.vttu.kpis.dto.request.PhanCongLanhDaoRequest;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.*;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.CongViecMapper;
import com.vttu.kpis.responsitory.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CongViecService {

    CongViecResponsitory congViecResponsitory;
    CongViecMapper congViecMapper;
    DonViResponsitory donViRepository;
    MucTieuReoisitory mucTieuRepository;
    QuyenResponsitory quyenRepository;
    NhanVienResponsitory nhanVienRepository;
    PhanCongDonViRespository phanCongDonViRepository;
    PhanCongLanhDaoRepository phanCongLanhDaoRepository;
    NhomMucTieuResponsitory nhomMucTieuResponsitory;
    public CongViecResponse createCongViec(CongViecRequest request){
        // Tạo đối tượng CongViec từ request
        CongViec congViec = congViecMapper.toCreateCongViec(request);

        // Lấy MucTieu từ repository dựa trên mã mục tiêu từ request
        MucTieu mucTieu = mucTieuRepository.findById(request.getMucTieu().getMamuctieu())
                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));

        NhomMucTieu nhomMucTieu = nhomMucTieuResponsitory.findById(request.getNhomMucTieu().getManhom())
                .orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED));

        // Khởi tạo tập hợp phân công đơn vị và phân công lãnh đạo
        Set<PhanCongDonVi> phanCongDonVis = new HashSet<>();
        Set<PhanCongLanhDao> phanCongLanhDaos = new HashSet<>();

        // Xử lý phân công đơn vị từ request
        for (PhanCongDonViRequest list : request.getPhanCongDonVis()) {
            PhanCongDonVi phanCongDonVi = new PhanCongDonVi();
            phanCongDonVi.setCongViec(congViec);
            phanCongDonVi.setDonVi(donViRepository.findById(list.getDonVi().getMadonvi())
                    .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED)));
            phanCongDonVi.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                    .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
            phanCongDonVis.add(phanCongDonVi);
        }

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

        // Gán MucTieu, PhanCongDonVi và PhanCongLanhDao cho CongViec
        congViec.setMucTieu(mucTieu);
        congViec.setPhanCongDonVis(phanCongDonVis);
        congViec.setPhanCongLanhDaos(phanCongLanhDaos);

        // Lưu CongViec và trả về CongViecResponse
        return congViecMapper.toCongViecResponse(congViecResponsitory.save(congViec));
    }
//    public CongViecResponse updateCongViec(UUID macongviec, CongViecRequest request) {
//        // Lấy đối tượng CongViec từ cơ sở dữ liệu dựa trên mã công việc
//        CongViec congViec = congViecResponsitory.findById(String.valueOf(macongviec))
//                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
//
//        // Lấy MucTieu từ repository dựa trên mã mục tiêu từ request
//        MucTieu mucTieu = mucTieuRepository.findById(request.getMucTieu().getMamuctieu())
//                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));
//
//        // Cập nhật thông tin công việc từ request
//        congViec.setTencongviec(request.getTencongviec());
//        congViec.setNgaybatdau(request.getNgaybatdau());
//        congViec.setNgayketthucdukien(request.getNgayketthucdukien());
//        congViec.setTrangthaicongviec(request.getTrangthaicongviec());
//        congViec.setMacongvieccha(request.getMacongvieccha());
//        congViec.setMucTieu(mucTieu);
//
//        // Lấy danh sách các ID của PhanCongDonVi và PhanCongLanhDao trong request
//        Set<Long> requestedPhanCongDonViIds = request.getPhanCongDonVis().stream()
//                .map(PhanCongDonViRequest::getMaphancongdonvi)
//                .collect(Collectors.toSet());
//
//        Set<Long> requestedPhanCongLanhDaoIds = request.getPhanCongLanhDaos().stream()
//                .map(PhanCongLanhDaoRequest::getMaphanconglanhdao)
//                .collect(Collectors.toSet());
//
//        // Xóa các PhanCongDonVi không có trong request từ cơ sở dữ liệu
//        phanCongDonViRepository.deleteAllById(
//                congViec.getPhanCongDonVis().stream()
//                        .filter(phanCongDonVi -> !requestedPhanCongDonViIds.contains(phanCongDonVi.getMaphancongdonvi()))
//                        .map(PhanCongDonVi::getMaphancongdonvi)
//                        .collect(Collectors.toList())
//        );
//
//
//        // Xóa các PhanCongLanhDao không có trong request từ cơ sở dữ liệu
//        phanCongLanhDaoRepository.deleteAllById(
//                congViec.getPhanCongLanhDaos().stream()
//                        .filter(phanCongLanhDao -> !requestedPhanCongLanhDaoIds.contains(phanCongLanhDao.getMaphanconglanhdao()))
//                        .map(PhanCongLanhDao::getMaphanconglanhdao)
//                        .collect(Collectors.toList())
//        );
//
//        // Cập nhật và thêm các PhanCongDonVi từ request
//        Set<PhanCongDonVi> phanCongDonVis = new HashSet<>();
//        for (PhanCongDonViRequest list : request.getPhanCongDonVis()) {
//            PhanCongDonVi phanCongDonVi = phanCongDonViRepository.findById(list.getMaphancongdonvi())
//                    .orElse(new PhanCongDonVi());
//            phanCongDonVi.setCongViec(congViec);
//            phanCongDonVi.setDonVi(donViRepository.findById(list.getDonVi().getMadonvi())
//                    .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED)));
//            phanCongDonVi.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
//                    .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
//            phanCongDonVis.add(phanCongDonVi);
//        }
//        congViec.setPhanCongDonVis(phanCongDonVis);
//
//        // Cập nhật và thêm các PhanCongLanhDao từ request
//        Set<PhanCongLanhDao> phanCongLanhDaos = new HashSet<>();
//        for (PhanCongLanhDaoRequest list : request.getPhanCongLanhDaos()) {
//            PhanCongLanhDao phanCongLanhDao = phanCongLanhDaoRepository.findById(list.getMaphanconglanhdao())
//                    .orElse(new PhanCongLanhDao());
//            phanCongLanhDao.setCongViec(congViec);
//            phanCongLanhDao.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
//                    .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
//            phanCongLanhDao.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
//                    .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
//            phanCongLanhDaos.add(phanCongLanhDao);
//        }
//        congViec.setPhanCongLanhDaos(phanCongLanhDaos);
//
//        // Lưu CongViec và trả về CongViecResponse
//        return congViecMapper.toCongViecResponse(congViecResponsitory.save(congViec));
//    }

    public void deleteUser(Long userId) {
        phanCongDonViRepository.deleteById(userId);
    }


    public CongViecResponse updateCongViec(UUID macongviec, CongViecRequest request) {
        // Lấy đối tượng CongViec từ cơ sở dữ liệu dựa trên mã công việc
        CongViec congViec = congViecResponsitory.findById(String.valueOf(macongviec))
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

        // Lấy MucTieu từ repository dựa trên mã mục tiêu từ request
        MucTieu mucTieu = mucTieuRepository.findById(request.getMucTieu().getMamuctieu())
                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));

        // Cập nhật thông tin công việc từ request
        congViec.setTencongviec(request.getTencongviec());
        congViec.setNgaybatdau(request.getNgaybatdau());
        congViec.setNgayketthucdukien(request.getNgayketthucdukien());
        congViec.setTrangthaicongviec(request.getTrangthaicongviec());
        congViec.setMacongvieccha(request.getMacongvieccha());
        congViec.setMucTieu(mucTieu);

        // Cập nhật tập hợp phân công đơn vị
        Set<PhanCongDonVi> phanCongDonVis = congViec.getPhanCongDonVis();
        for (PhanCongDonViRequest list : request.getPhanCongDonVis()) {
            // Kiểm tra xem phân công đơn vị đã tồn tại chưa
            if (!phanCongDonVis.stream().anyMatch(phanCong -> Objects.equals(phanCong.getMaphancongdonvi(), list.getMaphancongdonvi()))) {
                PhanCongDonVi phanCongDonVi = new PhanCongDonVi();
                phanCongDonVi.setCongViec(congViec);
                phanCongDonVi.setDonVi(donViRepository.findById(list.getDonVi().getMadonvi())
                        .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED)));
                phanCongDonVi.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                        .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                phanCongDonVis.add(phanCongDonVi);
            }
        }
        congViec.setPhanCongDonVis(phanCongDonVis);

        // Cập nhật tập hợp phân công lãnh đạo
        Set<PhanCongLanhDao> phanCongLanhDaos = congViec.getPhanCongLanhDaos();
        phanCongLanhDaos.clear();
        for (PhanCongLanhDaoRequest list : request.getPhanCongLanhDaos()) {
            PhanCongLanhDao phanCongLanhDao = phanCongLanhDaoRepository.findById(list.getMaphanconglanhdao())
                    .orElse(new PhanCongLanhDao());
            phanCongLanhDao.setCongViec(congViec);
            phanCongLanhDao.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                    .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
            phanCongLanhDao.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                    .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
            phanCongLanhDaos.add(phanCongLanhDao);
        }
        congViec.setPhanCongLanhDaos(phanCongLanhDaos);

        // Lưu CongViec và trả về CongViecResponse
        return congViecMapper.toCongViecResponse(congViecResponsitory.save(congViec));
    }

    public CongViecResponse getCongViecByMaCongViec(String macongviec){

        return congViecMapper.toCongViecResponse(congViecResponsitory.findByMacongviec(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED)));
    }

    public boolean CheckDonVi(String macongviec, int madonvi){

            if(congViecResponsitory.findbycongviec(macongviec,madonvi) > 0){
                return false;
            }
            else{
                phanCongDonViRepository.deleteByMaCongViecAndMaDonVi(macongviec,madonvi);
            }

        return true;
    }

    public boolean CheckBanhLanhDaoTaoCongViec(String macongviec, int manhanvien){

        if(congViecResponsitory.SelectCountBanLanhDao(macongviec,manhanvien) > 0){
            return false;
        }
        else {
            phanCongLanhDaoRepository.deleteByMaCongViecAndMaNhanVien(macongviec,manhanvien);
        }

        return true;
    }

    public List<CongViecResponse> getCongViecByMaNguoiTao (int madonvi,int ma_nguoitao){
        return congViecResponsitory.getCongViecDonViByNguoiTao(madonvi,ma_nguoitao).stream().map(congViecMapper::toCongViecResponse).toList();

    }
}
