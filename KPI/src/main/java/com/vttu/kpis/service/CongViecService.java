package com.vttu.kpis.service;

import com.vttu.kpis.dto.request.CongViecRequest;
import com.vttu.kpis.dto.request.GiaHanRequest;
import com.vttu.kpis.dto.request.PhanCongDonViRequest;
import com.vttu.kpis.dto.request.PhanCongLanhDaoRequest;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.*;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.CongViecMapper;
import com.vttu.kpis.responsitory.*;
import com.vttu.kpis.utils.DateConverter;
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
    TrangThaiCongViecResponsitory trangThaiCongViecResponsitory;
    KetQuaCongViecResponsitory ketQuaCongViecResponsitory;
    PhanCongNhanVienResponsitory phanCongNhanVienResponsitory;
    XacNhanRespository xacNhanRespository;
    GiaHanResponsitory giaHanResponsitory;

    @Transactional(rollbackFor = Exception.class)
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
        Date ngayhientai = new Date();
        // Gán MucTieu, PhanCongDonVi và PhanCongLanhDao cho CongViec
        congViec.setMucTieu(mucTieu);
        congViec.setPhanCongDonVis(phanCongDonVis);
        congViec.setPhanCongLanhDaos(phanCongLanhDaos);
        congViec.setThoigiantao(ngayhientai);
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



    @Transactional(rollbackFor = Exception.class)
    public CongViecResponse updateCongViec(String macongviec, CongViecRequest request) {
        // Lấy đối tượng CongViec từ cơ sở dữ liệu dựa trên mã công việc
        CongViec congViec = congViecResponsitory.findById(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        // Lấy MucTieu từ repository dựa trên mã mục tiêu từ request
         mucTieuRepository.findById(request.getMucTieu().getMamuctieu())
                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));
         nhomMucTieuResponsitory.findById(request.getNhomMucTieu().getManhom())
                 .orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED));

        // Cập nhật thông tin công việc từ request
        congViec.setTencongviec(request.getTencongviec());
        congViec.setNgaybatdau(request.getNgaybatdau());
        congViec.setNgayketthucdukien(request.getNgayketthucdukien());
     //   congViec.setTrangThaiCongViec(trangThaiCongViec);
        congViec.setMacongvieccha(request.getMacongvieccha());

        // Cập nhật tập hợp phân công đơn vị
        Set<PhanCongDonVi> phanCongDonVis = congViec.getPhanCongDonVis();
        Set<PhanCongDonViRequest> phancongdvrequest = request.getPhanCongDonVis();

        // Lấy các mã DonVi từ phancongdvrequest
        Set<Integer> maSetRequest = phancongdvrequest.stream()
                .map(requests -> requests.getDonVi().getMadonvi())
                .collect(Collectors.toSet());

        // Lấy các phần tử không trùng trong phanCongDonVis
        Set<PhanCongDonVi> nonDuplicatesInPhanCongDonVis = phanCongDonVis.stream()
                .filter(item -> !maSetRequest.contains(item.getDonVi().getMadonvi()))
                .collect(Collectors.toSet());
        // In kết quả
        System.out.println("Duplicates: " + nonDuplicatesInPhanCongDonVis);

        for (PhanCongDonVi i : nonDuplicatesInPhanCongDonVis){
                int madonvi = i.getDonVi().getMadonvi();

        Map<String,Object> trangthai = phanCongDonViRepository.findByTrangThaiCongViec(macongviec,madonvi);
            Object maTrangThaiObj = trangthai.get("ma_trangthai");
            if (maTrangThaiObj != null) {
                int maTrangThai = Integer.parseInt(maTrangThaiObj.toString());
                if (maTrangThai != 1) {
                    throw new AppException(ErrorCode.CongViec_Dang_ThucHien_ERROR, "Tên đơn vị: " + i.getDonVi().getTendonvi());
                }
            }
                long check = phanCongDonViRepository.countByCheckTruocKhiXoa(macongviec,madonvi);
                if(check > 0)
                    throw new AppException(ErrorCode.XoaDonVi_PhanCong,"Đơn vị: " + i.getDonVi().getTendonvi());
                else
                    phanCongDonViRepository.deleteByMaCongViecAndMaDonVi(macongviec,madonvi);
        }

        Set<PhanCongLanhDao> phanCongLanhDaos = congViec.getPhanCongLanhDaos();
        Set<PhanCongLanhDaoRequest> phancongldrequest = request.getPhanCongLanhDaos();

        Set<Integer>  maNvRequest = phancongldrequest.stream()
                .map(requestld -> requestld.getNhanVien().getManhanvien())
                .collect(Collectors.toSet());
        Set<PhanCongLanhDao> nonDuplicatesInPhanCongLanhDaos = phanCongLanhDaos.stream()
                .filter(item -> !maNvRequest.contains(item.getNhanVien().getManhanvien()))
                .collect(Collectors.toSet());

        for(PhanCongLanhDao i : nonDuplicatesInPhanCongLanhDaos){

            int manhanvien = i.getNhanVien().getManhanvien();

//            Map<String,Object> trangthai = phanCongNhanVienResponsitory.findByTrangThaiCongViec(macongviec,manhanvien);
//             hỏi lại
//            Object maTrangThaiObj = trangthai.get("ma_trangthai");
//            if (maTrangThaiObj != null) {
//                int maTrangThai = Integer.parseInt(maTrangThaiObj.toString());
//                if (maTrangThai != 1) {
//                    throw new AppException(ErrorCode.CongViec_Dang_ThucHien_ERROR, "Tên nhân viên: " + i.getNhanVien().getTennhanvien());
//                }
//            }

            long check = phanCongLanhDaoRepository.countByCheckTruocKhiXoa(macongviec,manhanvien);

            if(check > 0 )
                throw new AppException(ErrorCode.XoaNhanVien_BanLanhDao);
            else
                phanCongLanhDaoRepository.deleteByMaCongViecAndMaNhanVien(macongviec, manhanvien);

        }


        for (PhanCongDonViRequest list : request.getPhanCongDonVis()) {

            // Kiểm tra xem phân công đơn vị đã tồn tại chưa
                PhanCongDonVi phanCongDonVi = phanCongDonViRepository.listPhanCongCongViecByMaCongViecAnMaDonVi(macongviec,list.getDonVi().getMadonvi());
                PhanCongDonVi phanCongDonViNew = new PhanCongDonVi();
                if(phanCongDonVi != null){
                    phanCongDonVi.setDonVi(donViRepository.findById(list.getDonVi().getMadonvi())
                            .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED)));
                    phanCongDonVi.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                            .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                    phanCongDonVi.setThuchienchinh(list.isThuchienchinh());
                    phanCongDonVis.add(phanCongDonVi);
                }else {
                    phanCongDonViNew.setCongViec(congViec);
                    phanCongDonViNew.setDonVi(donViRepository.findById(list.getDonVi().getMadonvi())
                            .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED)));
                    phanCongDonViNew.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                            .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                    phanCongDonViNew.setThuchienchinh(list.isThuchienchinh());
                    phanCongDonVis.add(phanCongDonViNew);
                }
        }
        congViec.setPhanCongDonVis(phanCongDonVis);

        // Cập nhật tập hợp phân công lãnh đạo

        for (PhanCongLanhDaoRequest list : request.getPhanCongLanhDaos()) {

            PhanCongLanhDao phanCongLanhDao = phanCongLanhDaoRepository.listPhanCongLanhDaoByMaCongViecAnMaDonVi(macongviec,list.getNhanVien().getManhanvien());
            PhanCongLanhDao phanCongLanhDaoNew = new PhanCongLanhDao();
            if(phanCongLanhDao != null) {
                phanCongLanhDao.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                        .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
                phanCongLanhDao.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                        .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                congViec.setPhanCongLanhDaos(phanCongLanhDaos);
            }else {
                phanCongLanhDaoNew.setCongViec(congViec);
                phanCongLanhDaoNew.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                        .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
                phanCongLanhDaoNew.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                        .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
                phanCongLanhDaos.add(phanCongLanhDaoNew);
            }

        }
        congViec.setPhanCongLanhDaos(phanCongLanhDaos);

        // Lưu CongViec và trả về CongViecResponse
        return congViecMapper.toCongViecResponse(congViecResponsitory.save(congViec));
    }

    public CongViecResponse getCongViecByMaCongViec(String macongviec){

        return congViecMapper.toCongViecResponse(congViecResponsitory.findByMacongviec(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED)));
    }

//    public boolean CheckDonVi(String macongviec, int madonvi){
//
//            if(congViecResponsitory.findbycongviec(macongviec,madonvi) > 0){
//                return false;
//            }
//            else{
//                phanCongDonViRepository.deleteByMaCongViecAndMaDonVi(macongviec,madonvi);
//            }
//
//        return true;
//    }

//    public boolean CheckBanhLanhDaoTaoCongViec(String macongviec, int manhanvien){
//
//         nhanVienRepository.findById(manhanvien)
//                .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_FOUND));
//         congViecResponsitory.findById(macongviec)
//                 .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
//
//        if(congViecResponsitory.SelectCountBanLanhDao(macongviec,manhanvien) > 0){
//            return false;
//        }
//        else {
//            phanCongLanhDaoRepository.deleteByMaCongViecAndMaNhanVien(macongviec,manhanvien);
//        }
//
//        return true;
//    }

    public List<CongViecResponse> getCongViecByMaNguoiTao (int madonvi,int ma_nguoitao){
        return congViecResponsitory.getCongViecDonViByNguoiTao(madonvi,ma_nguoitao).stream().map(congViecMapper::toCongViecResponse).toList();

    }
    public List<CongViecResponse> getCongViecBanLanhDaoService (int manhanvien){
        return congViecResponsitory.getCongViecBanLanhDao(manhanvien).stream().map(congViecMapper::toCongViecResponse).toList();

    }

 //   @Transactional(rollbackFor = Exception.class)
    public float updatetrangthaicongviecService(String macongviec, int matrangthai){
        CongViec congViecNhanVien = congViecResponsitory.findById(macongviec.trim())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

        if(!congViecNhanVien.getMacongvieccha().equals("0")){
            CongViec congvieccha = congViecResponsitory.findByMacongviec(congViecNhanVien.getMacongvieccha())
                    .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
            if(congvieccha.isXacnhan())
                throw new AppException(ErrorCode.CongViec_Nhan_DaXacNhan);

        }

         congViecResponsitory.updateCongViecByTrangThaiCongViec(matrangthai,macongviec);
        float phantramhoanthanh = 0;
         int ketquacanhan = congViecNhanVien.getKetQuaCongViec().getMaketqua();
        // Kiểm tra kết quả công việc cá nhân

        if (ketquacanhan == 1 && matrangthai == 3) {
            phantramhoanthanh = 100;
        }
        congViecResponsitory.updatePhanTramHoanThanhCongViec(phantramhoanthanh, macongviec);
  //      float phantramhtcvcha = 0;
        // từ công việc của nhân viên ta thực hiện tìm các công việc cha và update phần trăm hoàn thành theo kết quả
//        while (congViecNhanVien != null) {
//
//            String macongvieccha = congViecNhanVien.getMacongvieccha();
//            String macongvieccon = congViecNhanVien.getMacongviec();
//            String xuat = congViecNhanVien.getMacongvieccha();
//            if(macongvieccha.equals("0")){
//                congViecNhanVien = congViecResponsitory.findById(macongvieccon)
//                        .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
//            }else {
//                congViecNhanVien = congViecResponsitory.findById(macongvieccha)
//                        .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
//            }
//
//
//            int ketquacvcha = congViecNhanVien.getKetQuaCongViec().getMaketqua();
//            float tongphantram = 0;
//            int soluong = 0;
//            if(ketquacvcha == 2 ){
//                    List<CongViec> congviecCha = congViecResponsitory.getCongViecConTheoMaCongViecChaGoc(macongvieccha);
//                        for (CongViec cv : congviecCha){
//                            List<CongViec> congviecCon = congViecResponsitory.getCongViecTheoMaCongViecGoc(cv.getMacongviec());
//                            for (CongViec cvc : congviecCon){
//                                soluong++;
//                                tongphantram += cvc.getPhantramhoanthanh();
//                            }
//                            if (soluong > 0) {
//                                phantramhtcvcha = tongphantram / soluong;
//                                congViecResponsitory.updatePhanTramHoanThanhCongViec(phantramhtcvcha, macongvieccha);
//                            }
//                        }
//
//            }
//            if (xuat.equals("0")) {
//                break;
//            }
//        }
        tinhPhanTramHoanThanhNgucLen(macongviec);

        return phantramhoanthanh;
    }

    public void tinhPhanTramHoanThanhNgucLen(String macongviec) {

        CongViec congViec = congViecResponsitory.findById(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

              float phantramhtcvcha = 0;
       //  từ công việc của nhân viên ta thực hiện tìm các công việc cha và update phần trăm hoàn thành theo kết quả
        while (congViec != null) {

            String macongvieccha = congViec.getMacongvieccha();
            String macongvieccon = congViec.getMacongviec();
            String xuat = congViec.getMacongvieccha();
            if(macongvieccha.equals("0")){
                congViec = congViecResponsitory.findById(macongvieccon)
                        .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
            }else {
                congViec = congViecResponsitory.findById(macongvieccha)
                        .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
            }


            int ketquacvcha = congViec.getKetQuaCongViec().getMaketqua();
            float tongphantram = 0;
            int soluong = 0;
            if(ketquacvcha == 2 ){
                    List<CongViec> congviecCha = congViecResponsitory.getCongViecConTheoMaCongViecChaGoc(macongvieccha);
                        for (CongViec cv : congviecCha){
                            List<Map<String,Object>> congviecCon = congViecResponsitory.getCongViecTheoMaCongViecGoc(cv.getMacongviec());
                    System.err.println(congviecCon);
                       for (Map<String,Object> cvc : congviecCon){
                                soluong++;
                                tongphantram += Float.parseFloat(cvc.get("phantramhoanthanh").toString());
                            }
                            if (soluong > 0) {
                                phantramhtcvcha = tongphantram / soluong;
                                congViecResponsitory.updatePhanTramHoanThanhCongViec(phantramhtcvcha, macongvieccha);
                            }
                        }

            }
            if (xuat.equals("0")) {
                break;
            }
        }
    }

    public float UpdateTinhKetQuaCongViec(String macongviec, int maketqua) {
        CongViec congViec = congViecResponsitory.findById(macongviec).orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        ketQuaCongViecResponsitory.findById(maketqua).orElseThrow(() -> new AppException(ErrorCode.KetQua_NOT_EXISTED));
        float phantramkq = 0;
        if (!congViec.getMacongvieccha().equals("0")) {

            CongViec congvieccha = congViecResponsitory.findByMacongviec(congViec.getMacongvieccha())
                    .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

            if (congvieccha.isXacnhan())
                throw new AppException(ErrorCode.CongViec_Nhan_DaXacNhan);
            congViecResponsitory.updateTinhKetQuaCongViec(maketqua, macongviec);

            if (maketqua == 1) {
                if (congViec.getTrangThaiCongViec().getMatrangthai() == 3)
                    phantramkq = 100;
                congViecResponsitory.updatePhanTramHoanThanhCongViec(phantramkq, macongviec);
            } else if (maketqua == 3) {
                congViecResponsitory.updatePhanTramHoanThanhCongViec(0, macongviec);
            } else {
                float tongphantram = 0;
                List<CongViec> congViecs = congViecResponsitory.getCongViecConTheoMaCongViecCha(macongviec);
                for (CongViec cv : congViecs) {
                    tongphantram += cv.getPhantramhoanthanh();
                }
                if (!congViecs.isEmpty()) {
                    phantramkq = tongphantram / congViecs.size();
                    congViecResponsitory.updatePhanTramHoanThanhCongViec(phantramkq, macongviec);
                }
            }

            tinhPhanTramHoanThanhNgucLen(macongviec);
        }else {

            CongViec congviec = congViecResponsitory.findById(macongviec)
                    .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

            if (congviec.isXacnhan())
                throw new AppException(ErrorCode.CongViec_Nhan_DaXacNhan);
            congViecResponsitory.updateTinhKetQuaCongViec(maketqua, macongviec);

            if (maketqua == 1) {
                if (congViec.getTrangThaiCongViec().getMatrangthai() == 3)
                    phantramkq = 100;
                congViecResponsitory.updatePhanTramHoanThanhCongViec(phantramkq, macongviec);
            } else if (maketqua == 3) {
                congViecResponsitory.updatePhanTramHoanThanhCongViec(0, macongviec);
            } else {
                float tongphantram = 0;
                List<CongViec> congViecs = congViecResponsitory.getCongViecConTheoMaCongViecCha(macongviec);
                for (CongViec cv : congViecs) {
                    tongphantram += cv.getPhantramhoanthanh();
                }
                if (!congViecs.isEmpty()) {
                    phantramkq = tongphantram / congViecs.size();
                    congViecResponsitory.updatePhanTramHoanThanhCongViec(phantramkq, macongviec);
                }
            }
        }
        return phantramkq;

    }

    public float UpdateTuNhapKetQuaCongViec(String macongviec, float phantram) {

      CongViec cv =  congViecResponsitory.findById(macongviec).orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        CongViec congvieccha = congViecResponsitory.findByMacongviec(cv.getMacongvieccha())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        if(congvieccha.isXacnhan())
            throw new AppException(ErrorCode.CongViec_Nhan_DaXacNhan);
        if(phantram < 0){
            throw new AppException(ErrorCode.CongViec_NOT_EXISTED);
        }else if(phantram > 100){
            throw new AppException(ErrorCode.CongViec_NOT_EXISTED);
        }

        if(cv.getKetQuaCongViec().getMaketqua() == 3){
            congViecResponsitory.updatePhanTramKetQuaCongViec(phantram,macongviec);
        }
        tinhPhanTramHoanThanhNgucLen(macongviec);
        return phantram;
    }

    public boolean YeuCauXacNhan(String macongviec) {
            CongViec congViec = congViecResponsitory.findById(macongviec)
                    .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
            congViec.setYeucauxacnhan(true);
            congViecResponsitory.save(congViec);
        return  true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean XacNhanYeuCauHoanThanhCongViec(String macongviec, boolean xacnhan, String noidung) {
        CongViec congViec = congViecResponsitory.findById(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

      if(xacnhan){
          congViec.setXacnhan(true);
          XacNhan xacNhan = new XacNhan();
          xacNhan.setNoidung(noidung);
          xacNhan.setTrangthai(true);
          xacNhan.setCongViec(congViec);
          xacNhanRespository.save(xacNhan);
          LocalDate ngayketthuc = congViec.getNgayketthucdukien();
          LocalDate ngayhientai = LocalDate.now();
          boolean trehan = false;
          if (ngayketthuc.isAfter(ngayhientai)) {
              System.out.println("Ngày kết thúc sau ngày hiện tại.");
              trehan = true;
              congViecResponsitory.updatecongvieckhitrehan(true,macongviec);
          }
      }else {
          if(noidung == null){
              throw new AppException(ErrorCode.CongViec_NOT_EXISTED);
          }
            XacNhan xacNhan = new XacNhan();
          xacNhan.setNoidung(noidung);
          xacNhan.setTrangthai(false);
          xacNhan.setCongViec(congViec);
          xacNhanRespository.save(xacNhan);
          return false;
      }
        congViecResponsitory.save(congViec);
        return true;
    }


    public boolean xoaCongViecConBoPhanNhan(String macongviec){

     congViecResponsitory.findById(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

        Map<String,Object> trangthai = phanCongNhanVienResponsitory.findByTrangThaiCongViecNhanVienXoa(macongviec);

        Object maTrangThaiObj = trangthai.get("ma_trangthai");
        if (maTrangThaiObj != null) {
            int maTrangThai = Integer.parseInt(maTrangThaiObj.toString());
            if (maTrangThai == 2) {
                throw new AppException(ErrorCode.CongViec_Dang_ThucHien_ERROR);

            }else if(maTrangThai == 3)
                throw new AppException(ErrorCode.CongViec_Da_ThucHien_ERROR);
        }
        long check = phanCongNhanVienResponsitory.countByTruongBPCheckTruocKhiXoa(macongviec);

        if(check > 0 )
            throw new AppException(ErrorCode.XoaNhanVien_BanLanhDao);
        else{
        //    phanCongNhanVienResponsitory.deleteByMaCongViecAndMaNhanVien(macongviec,)f
           phanCongNhanVienResponsitory.deleteByMaCongViec(macongviec);
            congViecResponsitory.deleteCongViec(macongviec);
        }

        return  true;
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean YeuCauGiaHanCongViec(GiaHanRequest giaHanRequest) {
        CongViec congViec = congViecResponsitory.findById(giaHanRequest.getCongViec().getMacongviec())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        if(giaHanRequest.getThoigian().equals(""))
            throw new AppException(ErrorCode.ThoiGian_Is_Empty);
        if(giaHanRequest.getLydo().isEmpty())
            throw new AppException(ErrorCode.LyDo_Is_Empty);
        GiaHan giaHan = new GiaHan();
        giaHan.setCongViec(congViec);
        giaHan.setThoigian(giaHanRequest.getThoigian());
        giaHan.setLydo(giaHanRequest.getLydo());
        giaHan.setTrangthai(false);
        giaHan.setMagiahancha(0);
        congViecResponsitory.yeucaugiahancongviec(true,giaHanRequest.getCongViec().getMacongviec());
        giaHanResponsitory.save(giaHan);

        return  true;
    }
    public boolean XacNhanYeuCauGiaHanCongViec(GiaHanRequest giaHanRequest,boolean xacnhan, String lydokhonggiahan){
        CongViec congViec = congViecResponsitory.findById(giaHanRequest.getCongViec().getMacongviec())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        GiaHan giaHan = new GiaHan();
        giaHan.setCongViec(congViec);
        giaHan.setThoigian(giaHanRequest.getThoigian());

        if(xacnhan){
            String macongviec = congViec.getMacongviec();
            if(!congViec.getMacongvieccha().equals("0") )
                macongviec = congViec.getMacongvieccha();
            CongViec cong = congViecResponsitory.findById(macongviec)
                    .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
            LocalDate ngayketthuccvcha = cong.getNgayketthucdukien();
            LocalDate ngayktcvcon = giaHanRequest.getThoigian();
            if(ngayktcvcon.isAfter(ngayketthuccvcha))
                throw new AppException(ErrorCode.ErrorNgayKTConViecCon, "Ngày kết thúc: " + DateConverter.converDate(cong.getNgayketthucdukien()) + " trong khi ngày gia hạn là: " + DateConverter.converDate(giaHanRequest.getThoigian()));

            congViecResponsitory.updatecongvieckhixacnhangiahan(xacnhan, giaHanRequest.getThoigian(),giaHanRequest.getCongViec().getMacongviec());
            giaHan.setLydo(giaHanRequest.getLydo());
        }else {
            if(lydokhonggiahan.isEmpty())
                throw new AppException(ErrorCode.LyDo_Is_Empty);
            giaHan.setLydo(lydokhonggiahan);
            if(giaHanRequest.getThoigian().equals(""))
                throw new AppException(ErrorCode.ThoiGian_Is_Empty);
            if(giaHanRequest.getLydo().isEmpty())
                throw new AppException(ErrorCode.LyDo_Is_Empty);
            congViecResponsitory.yeucaugiahancongviec(false,giaHanRequest.getCongViec().getMacongviec());
        }
        giaHan.setTrangthai(xacnhan);
        giaHan.setMagiahancha(giaHanRequest.getMagiahan());
        giaHanResponsitory.save(giaHan);
        
        return true;
    }
}
