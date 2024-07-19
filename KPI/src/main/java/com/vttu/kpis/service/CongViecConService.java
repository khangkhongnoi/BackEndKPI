package com.vttu.kpis.service;

import com.vttu.kpis.dto.request.*;
import com.vttu.kpis.dto.response.CongViecConNhanVienResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.*;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.CongViecConNhanVienMapper;
import com.vttu.kpis.mapper.CongViecMapper;
import com.vttu.kpis.responsitory.*;
import com.vttu.kpis.utils.DateConverter;
import jakarta.validation.constraints.Null;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    CongViecMapper congViecMapper;
    PhanCongNhanVienResponsitory phanCongNhanVienResponsitory;
    BoPhanResponsitory boPhanResponsitory;
    @Transactional(rollbackFor = Exception.class)
    public CongViecConNhanVienResponse createCongViec(CongViecConNhanVienRequest request){



        CongViecConNhanVienResponse congViecConNhanVienResponse = new CongViecConNhanVienResponse();

      // kiểm tra sự tồn tại của công việc
        CongViec cong = congViecResponsitory.findByMacongviec(request.getMacongvieccha())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        TrangThaiCongViec trangThaiCongViec = new TrangThaiCongViec();
        trangThaiCongViec.setMatrangthai(1);
        // Tạo đối tượng CongViec từ request
        CongViec congViec = congViecConNhanVienMapper.toCreateCongViec(request);
                congViec.setTrangThaiCongViec(trangThaiCongViec);
                congViec.setPhantramhoanthanh(0);
        // Lấy MucTieu từ repository dựa trên mã mục tiêu từ request
        MucTieu mucTieu = mucTieuRepository.findById(cong.getMucTieu().getMamuctieu())
                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));

       danhChoResponsitory.findById(request.getDanhCho().getMadanhcho())
                .orElseThrow(() -> new AppException(ErrorCode.DanhCho_NOT_EXISTED));

        NhomMucTieu nhomMucTieu = nhomMucTieuResponsitory.findById(cong.getNhomMucTieu().getManhom())
                .orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED));

        // kiểm tra ngay bắt đầu và ngày kết thúc công việc con phải nằm trong ngay của công việc cha

        LocalDate ngaybatdaucvcha = cong.getNgaybatdau();
        LocalDate ngayketthuccvcha = cong.getNgayketthucdukien();

        LocalDate ngaybdcvcon = request.getNgaybatdau();
        LocalDate ngayktcvcon = request.getNgayketthucdukien();

//        if (ngaybdcvcon.isBefore(ngaybatdaucvcha)) {
//            System.out.println("Ngaybdcvcon is before Ngaybatdaucvcha.");
//        } else if (ngaybdcvcon.isAfter(ngaybatdaucvcha)) {
//            System.out.println("Ngaybdcvcon is after Ngaybatdaucvcha.");
//        } else {
//            System.out.println("Ngaybdcvcon is equal to Ngaybatdaucvcha.");
//        }
        if(ngaybdcvcon.isBefore(ngaybatdaucvcha))
            throw new AppException(ErrorCode.ErrorNgayBDConViecCon,"Ngày bắt đầu: " + DateConverter.converDate(cong.getNgaybatdau()) );
        if(ngayktcvcon.isAfter(ngayketthuccvcha))
            throw new AppException(ErrorCode.ErrorNgayKTConViecCon, "Ngày kết thúc: " + DateConverter.converDate(cong.getNgayketthucdukien()));

        // Khởi tạo tập hợp phân công đơn vị và phân công lãnh đạo
        // phanconglanhdao đóng vai trò là nhân viên thực hiện công việc được giao

        if(request.getDanhCho().getMadanhcho() == 1) {

            if(request.getPhanCongNhanViens().isEmpty())
              throw  new AppException(ErrorCode.PhanCongNhanVienIsEmpty);

            Set<PhanCongNhanVien> phanCongNhanViens = new HashSet<>();
            // Xử lý phân công lãnh đạo từ request
            for (PhanCongNhanVienRequest list : request.getPhanCongNhanViens()) {
                PhanCongNhanVien phanCongNhanVien = new PhanCongNhanVien();
                phanCongNhanVien.setCongViec(congViec);
                phanCongNhanVien.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                        .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
                phanCongNhanVien.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                        .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                phanCongNhanViens.add(phanCongNhanVien);
            }
            congViec.setPhanCongNhanViens(phanCongNhanViens);
        }
        else if (request.getDanhCho().getMadanhcho() == 2) {

            if(request.getPhanCongBoPhans().isEmpty())
                throw new AppException(ErrorCode.PhanCongBoPhanIsEmpty);

            Set<PhanCongBoPhan> phanCongBoPhans = new HashSet<>();
            for(PhanCongBoPhanRequest list : request.getPhanCongBoPhans()){
                PhanCongBoPhan phanCongBoPhan = new PhanCongBoPhan();
                phanCongBoPhan.setCongViec(congViec);
                phanCongBoPhan.setBoPhan(boPhanResponsitory.findById(list.getBoPhan().getMabophan())
                        .orElseThrow(() -> new AppException(ErrorCode.BoPhan_NOT_EXISTED)));
                phanCongBoPhan.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                        .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                phanCongBoPhans.add(phanCongBoPhan);
            }
            congViec.setPhanCongBoPhans(phanCongBoPhans);

        }
        else if(request.getDanhCho().getMadanhcho() == 3){

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
        Date ngayhientai = new Date();
        // Gán MucTieu, PhanCongDonVi và PhanCongLanhDao cho CongViec
        congViec.setMucTieu(mucTieu);
        congViec.setNhomMucTieu(nhomMucTieu);
        congViec.setThoigiantao(ngayhientai);

        // Lưu CongViec và trả về CongViecResponse
        return congViecConNhanVienMapper.toCongViecConNhanVienResponse(congViecResponsitory.save(congViec));

    }

    @Transactional(rollbackFor = Exception.class)
    public CongViecConNhanVienResponse createCongViecConBoPhanNhan(CongViecConNhanVienRequest request){


        // kiểm tra sự tồn tại của công việc
        CongViec cong = congViecResponsitory.findByMacongviec(request.getMacongvieccha())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        TrangThaiCongViec trangThaiCongViec = new TrangThaiCongViec();
        trangThaiCongViec.setMatrangthai(1);
        // Tạo đối tượng CongViec từ request
        CongViec congViec = congViecConNhanVienMapper.toCreateCongViec(request);
        congViec.setTrangThaiCongViec(trangThaiCongViec);
        congViec.setPhantramhoanthanh(0);
        // Lấy MucTieu từ repository dựa trên mã mục tiêu từ request
        MucTieu mucTieu = mucTieuRepository.findById(cong.getMucTieu().getMamuctieu())
                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));

        danhChoResponsitory.findById(request.getDanhCho().getMadanhcho())
                .orElseThrow(() -> new AppException(ErrorCode.DanhCho_NOT_EXISTED));

        NhomMucTieu nhomMucTieu = nhomMucTieuResponsitory.findById(cong.getNhomMucTieu().getManhom())
                .orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED));

        // kiểm tra ngay bắt đầu và ngày kết thúc công việc con phải nằm trong ngay của công việc cha

        LocalDate ngaybatdaucvcha = cong.getNgaybatdau();
        LocalDate ngayketthuccvcha = cong.getNgayketthucdukien();

        LocalDate ngaybdcvcon = request.getNgaybatdau();
        LocalDate ngayktcvcon = request.getNgayketthucdukien();

        if(ngaybdcvcon.isBefore(ngaybatdaucvcha))
            throw new AppException(ErrorCode.ErrorNgayBDConViecCon,"Ngày bắt đầu: " + DateConverter.converDate(cong.getNgaybatdau()) );
        if(ngayktcvcon.isAfter(ngayketthuccvcha))
            throw new AppException(ErrorCode.ErrorNgayKTConViecCon, "Ngày kết thúc: " + DateConverter.converDate(cong.getNgayketthucdukien()));

            if(request.getPhanCongNhanViens().isEmpty())
                throw  new AppException(ErrorCode.PhanCongNhanVienIsEmpty);

            Set<PhanCongNhanVien> phanCongNhanViens = new HashSet<>();
            // Xử lý phân công lãnh đạo từ request
            for (PhanCongNhanVienRequest list : request.getPhanCongNhanViens()) {
                PhanCongNhanVien phanCongNhanVien = new PhanCongNhanVien();
                phanCongNhanVien.setCongViec(congViec);
                phanCongNhanVien.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                        .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
                phanCongNhanVien.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                        .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                phanCongNhanViens.add(phanCongNhanVien);
            }
            congViec.setPhanCongNhanViens(phanCongNhanViens);
 
        Date ngayhientai = new Date();
        // Gán MucTieu, PhanCongDonVi và PhanCongLanhDao cho CongViec
        congViec.setMucTieu(mucTieu);
        congViec.setNhomMucTieu(nhomMucTieu);
        congViec.setThoigiantao(ngayhientai);

        // Lưu CongViec và trả về CongViecResponse
        return congViecConNhanVienMapper.toCongViecConNhanVienResponse(congViecResponsitory.save(congViec));

    }

    @Transactional(rollbackFor = Exception.class)
    public CongViecConNhanVienResponse updateCongViecConBoPhanNhan(String macongvieccon,CongViecConNhanVienRequest request){


        // kiểm tra sự tồn tại của công việc
        CongViec cong = congViecResponsitory.findByMacongviec(request.getMacongvieccha())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        // Tạo đối tượng CongViec từ request
        CongViec congViec = congViecConNhanVienMapper.toCreateCongViec(request);

        // kiểm tra ngay bắt đầu và ngày kết thúc công việc con phải nằm trong ngay của công việc cha

        LocalDate ngaybatdaucvcha = cong.getNgaybatdau();
        LocalDate ngayketthuccvcha = cong.getNgayketthucdukien();

        LocalDate ngaybdcvcon = request.getNgaybatdau();
        LocalDate ngayktcvcon = request.getNgayketthucdukien();

        if(ngaybdcvcon.isBefore(ngaybatdaucvcha))
            throw new AppException(ErrorCode.ErrorNgayBDConViecCon,"Ngày bắt đầu: " + DateConverter.converDate(cong.getNgaybatdau()) );
        if(ngayktcvcon.isAfter(ngayketthuccvcha))
            throw new AppException(ErrorCode.ErrorNgayKTConViecCon, "Ngày kết thúc: " + DateConverter.converDate(cong.getNgayketthucdukien()));

        if(request.getPhanCongNhanViens().isEmpty())
            throw new AppException(ErrorCode.PhanCongNhanVienIsEmpty);

        Set<PhanCongNhanVien> phanCongNhanViens = congViec.getPhanCongNhanViens();
        Set<PhanCongNhanVienRequest> phanCongNhanVienRequests = request.getPhanCongNhanViens();
        Set<Integer> maNvRequest = phanCongNhanVienRequests.stream()
                .map(requestld -> requestld.getNhanVien().getManhanvien())
                .collect(Collectors.toSet());

        Set<PhanCongNhanVien> noDuplicationPhanCongNhanVien = phanCongNhanViens.stream()
                .filter(item -> !maNvRequest.contains(item.getNhanVien().getManhanvien()))
                .collect(Collectors.toSet());

        for(PhanCongNhanVien i : noDuplicationPhanCongNhanVien){
            int manhanvien = i.getNhanVien().getManhanvien();
            long check = phanCongNhanVienResponsitory.countByCheckTruocKhiXoa(macongvieccon,manhanvien);

//            if(check > 0 )
//                throw new AppException(ErrorCode.XoaNhanVien_BanLanhDao )
        }

//        if(request.getDanhCho().getMadanhcho() == 1) {
//
//            if(request.getPhanCongNhanViens().isEmpty())
//                throw  new AppException(ErrorCode.PhanCongNhanVienIsEmpty);
//
//            Set<PhanCongNhanVien> phanCongNhanViens = new HashSet<>();
//            // Xử lý phân công lãnh đạo từ request
//            for (PhanCongNhanVienRequest list : request.getPhanCongNhanViens()) {
//                PhanCongNhanVien phanCongNhanVien = new PhanCongNhanVien();
//                phanCongNhanVien.setCongViec(congViec);
//                phanCongNhanVien.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
//                        .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
//                phanCongNhanVien.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
//                        .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
//                phanCongNhanViens.add(phanCongNhanVien);
//            }
//            congViec.setPhanCongNhanViens(phanCongNhanViens);
//        }
        Date ngayhientai = new Date();
        // Gán MucTieu, PhanCongDonVi và PhanCongLanhDao cho CongViec
        congViec.setThoigiantao(ngayhientai);

        // Lưu CongViec và trả về CongViecResponse
        return congViecConNhanVienMapper.toCongViecConNhanVienResponse(congViecResponsitory.save(congViec));

    }

    public List<CongViecResponse> getCongViecConTheoMaCongViecChaService(String macongvieccha){
        return congViecResponsitory.getCongViecConTheoMaCongViecCha(macongvieccha).stream().map(congViecMapper::toCongViecResponse).toList();
    }

    public CongViecResponse getChiTietCongViecCon(String macongvieccon) {
        return congViecMapper.toCongViecResponse(congViecResponsitory.findById(macongvieccon)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED)));
    }
    @Transactional(rollbackFor = Exception.class)
    public CongViecConNhanVienResponse updateCongViecCon(String macongviec, CongViecConNhanVienRequest request) {

        CongViec congViec = congViecResponsitory.findById(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

        // kiểm tra sự tồn tại của công việc
        CongViec cong = congViecResponsitory.findByMacongviec(request.getMacongvieccha())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

        // Lấy MucTieu từ repository dựa trên mã mục tiêu từ request
        MucTieu mucTieu = mucTieuRepository.findById(cong.getMucTieu().getMamuctieu())
                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));

        danhChoResponsitory.findById(request.getDanhCho().getMadanhcho())
                .orElseThrow(() -> new AppException(ErrorCode.DanhCho_NOT_EXISTED));

        NhomMucTieu nhomMucTieu = nhomMucTieuResponsitory.findById(cong.getNhomMucTieu().getManhom())
                .orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED));

        DanhCho danhChoDB = danhChoResponsitory.findById(congViec.getDanhCho().getMadanhcho())
                .orElseThrow(() -> new AppException(ErrorCode.DanhCho_NOT_EXISTED));
        if (danhChoDB.getMadanhcho() != request.getDanhCho().getMadanhcho()) {

        }

        LocalDate ngaybatdaucvcha = cong.getNgaybatdau();
        LocalDate ngayketthuccvcha = cong.getNgayketthucdukien();

        LocalDate ngaybdcvcon = request.getNgaybatdau();
        LocalDate ngayktcvcon = request.getNgayketthucdukien();

        if (ngaybdcvcon.isBefore(ngaybatdaucvcha))
            throw new AppException(ErrorCode.ErrorNgayBDConViecCon, "Ngày bắt đầu: " + DateConverter.converDate(cong.getNgaybatdau()));
        if (ngayktcvcon.isAfter(ngayketthuccvcha))
            throw new AppException(ErrorCode.ErrorNgayKTConViecCon, "Ngày kết thúc: " + DateConverter.converDate(cong.getNgayketthucdukien()));


        congViec.setTencongviec(request.getTencongviec());
        congViec.setNgaybatdau(request.getNgaybatdau());
        congViec.setNgayketthucdukien(request.getNgayketthucdukien());
        //   congViec.setTrangThaiCongViec(trangThaiCongViec);
        congViec.setMacongvieccha(request.getMacongvieccha());
        congViec.setMucTieu(mucTieu);

        if (request.getDanhCho().getMadanhcho() == 1) {

            Set<PhanCongNhanVien> phanCongNhanViens = congViec.getPhanCongNhanViens();
            Set<PhanCongNhanVienRequest> phancongldrequest = request.getPhanCongNhanViens();

            Set<Integer> maNvRequest = phancongldrequest.stream()
                    .map(requestld -> requestld.getNhanVien().getManhanvien())
                    .collect(Collectors.toSet());
            Set<PhanCongNhanVien> nonDuplicatesInPhanCongLanhDaos = phanCongNhanViens.stream()
                    .filter(item -> !maNvRequest.contains(item.getNhanVien().getManhanvien()))
                    .collect(Collectors.toSet());


            if (request.getPhanCongNhanViens().isEmpty())
                throw new AppException(ErrorCode.PhanCongNhanVienIsEmpty);

            for (PhanCongNhanVien i : nonDuplicatesInPhanCongLanhDaos) {

                int manhanvien = i.getNhanVien().getManhanvien();
                long check = phanCongNhanVienResponsitory.countByCheckTruocKhiXoa(macongviec, manhanvien);

                if (check > 0)
                    throw new AppException(ErrorCode.XoaNhanVien_BanLanhDao, "Tên nhân viên: " + i.getNhanVien().getTennhanvien());
                else
                    phanCongNhanVienResponsitory.deleteByMaCongViecAndMaNhanVien(macongviec, manhanvien);
            }

            for (PhanCongNhanVienRequest list : request.getPhanCongNhanViens()) {
                PhanCongNhanVien phanCongNhanVien = phanCongNhanVienResponsitory.listPhanCongNhanVienByMaCongViecAnMaNhanVien(macongviec, list.getNhanVien().getManhanvien());
                PhanCongNhanVien phanCongNhanVienNew = new PhanCongNhanVien();
                if (phanCongNhanVien != null) {
                    phanCongNhanVien.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                            .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
                    phanCongNhanVien.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                            .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                    phanCongNhanViens.add(phanCongNhanVien);
                } else {
                    phanCongNhanVienNew.setCongViec(congViec);
                    phanCongNhanVienNew.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                            .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
                    phanCongNhanVienNew.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                            .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                    phanCongNhanViens.add(phanCongNhanVienNew);
                }
                congViec.setPhanCongNhanViens(phanCongNhanViens);

                // Xử lý phân công lãnh đạo từ request
            }
        }
        else if(request.getDanhCho().getMadanhcho() == 2){

            Set<PhanCongBoPhan> phanCongBoPhans = congViec.getPhanCongBoPhans();
            Set<PhanCongBoPhanRequest> congBoPhanRequests = request.getPhanCongBoPhans();
            Set<Integer> maBPRequest = congBoPhanRequests.stream()
                    .map(requestid -> requestid.getBoPhan().getMabophan())
                    .collect(Collectors.toSet());
            Set<PhanCongBoPhan> nonDuplication = phanCongBoPhans.stream()
                    .filter(item -> !maBPRequest.contains(item.getBoPhan().getMabophan()))
                    .collect(Collectors.toSet());

            if(request.getPhanCongBoPhans().isEmpty())
                throw new AppException(ErrorCode.PhanCongBoPhanIsEmpty);

            for(PhanCongBoPhan i : nonDuplication){
                int mabophan = i.getBoPhan().getMabophan();

            }
        }
            return congViecConNhanVienMapper.toCongViecConNhanVienResponse(congViecResponsitory.save(congViec));

        }


    @Transactional(rollbackFor = Exception.class)
    public CongViecConNhanVienResponse createCongViecConNhanVienNhan(CongViecConNhanVienRequest request){

        // kiểm tra sự tồn tại của công việc
        CongViec cong = congViecResponsitory.findByMacongviec(request.getMacongvieccha())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        TrangThaiCongViec trangThaiCongViec = new TrangThaiCongViec();
        trangThaiCongViec.setMatrangthai(1);
        KetQuaCongViec ketQuaCongViec = new KetQuaCongViec();
        ketQuaCongViec.setMaketqua(1);
        // Tạo đối tượng CongViec từ request
        CongViec congViec = congViecConNhanVienMapper.toCreateCongViec(request);
        congViec.setTrangThaiCongViec(trangThaiCongViec);
        congViec.setPhantramhoanthanh(0);


        // kiểm tra ngay bắt đầu và ngày kết thúc công việc con phải nằm trong ngay của công việc cha

        LocalDate ngaybatdaucvcha = cong.getNgaybatdau();
        LocalDate ngayketthuccvcha = cong.getNgayketthucdukien();

        LocalDate ngaybdcvcon = request.getNgaybatdau();
        LocalDate ngayktcvcon = request.getNgayketthucdukien();

        if(ngaybdcvcon.isBefore(ngaybatdaucvcha))
            throw new AppException(ErrorCode.ErrorNgayBDConViecCon,"Ngày bắt đầu: " + DateConverter.converDate(cong.getNgaybatdau()) );
        if(ngayktcvcon.isAfter(ngayketthuccvcha))
            throw new AppException(ErrorCode.ErrorNgayKTConViecCon, "Ngày kết thúc: " + DateConverter.converDate(cong.getNgayketthucdukien()));

        if(request.getPhanCongNhanViens().isEmpty())
            throw  new AppException(ErrorCode.PhanCongNhanVienIsEmpty);

        Set<PhanCongNhanVien> phanCongNhanViens = new HashSet<>();
        // Xử lý phân công lãnh đạo từ request
        for (PhanCongNhanVienRequest list : request.getPhanCongNhanViens()) {
            PhanCongNhanVien phanCongNhanVien = new PhanCongNhanVien();
            phanCongNhanVien.setCongViec(congViec);
            phanCongNhanVien.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                    .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
            phanCongNhanVien.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                    .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
            phanCongNhanViens.add(phanCongNhanVien);
        }
        congViec.setPhanCongNhanViens(phanCongNhanViens);

        Date ngayhientai = new Date();
        // Gán MucTieu, PhanCongDonVi và PhanCongLanhDao cho CongViec
        congViec.setThoigiantao(ngayhientai);
        congViec.setKetQuaCongViec(ketQuaCongViec);

        // Lưu CongViec và trả về CongViecResponse
        return congViecConNhanVienMapper.toCongViecConNhanVienResponse(congViecResponsitory.save(congViec));

    }
}
