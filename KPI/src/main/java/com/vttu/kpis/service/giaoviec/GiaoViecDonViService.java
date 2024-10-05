package com.vttu.kpis.service.giaoviec;

import com.vttu.kpis.dto.request.CongViecRequest;
import com.vttu.kpis.dto.request.PhanCongBoPhanRequest;
import com.vttu.kpis.dto.request.PhanCongDonViRequest;
import com.vttu.kpis.dto.request.PhanCongLanhDaoRequest;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.*;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.CongViecMapper;
import com.vttu.kpis.responsitory.*;
import com.vttu.kpis.responsitory.giaoviec.GiaoViecDonVịResponsitory;
import com.vttu.kpis.responsitory.log.Log_GiaoViecResponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GiaoViecDonViService {


    CongViecResponsitory congViecResponsitory;
    GiaoViecDonVịResponsitory giaoViecDonVịResponsitory;
    CongViecMapper congViecMapper;
    DonViResponsitory donViRepository;
    MucTieuReoisitory mucTieuRepository;
    QuyenResponsitory quyenRepository;
    NhanVienResponsitory nhanVienRepository;
    NhomMucTieuResponsitory nhomMucTieuResponsitory;
    PhanCongLanhDaoRepository phanCongLanhDaoRepository;
    PhanCongDonViRespository phanCongDonViRepository;
    Log_GiaoViecResponsitory logGiaoViecResponsitory;
    private final Log_GiaoViecResponsitory log_GiaoViecResponsitory;

    @Transactional(rollbackFor = Exception.class)
    public CongViecResponse createCongViecGiaoDonVi(CongViecRequest request){
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

        if(request.getPhanCongLanhDaos().isEmpty())
            throw new AppException(ErrorCode.PhanCongLanhDaoIsEmpty);
        if(request.getPhanCongDonVis().isEmpty())
            throw new AppException(ErrorCode.PhanCongDonViIsEmpty);
        // Xử lý phân công đơn vị từ request
        StringBuilder madonvithuchien = new StringBuilder();
        StringBuilder madonviphohop =  new StringBuilder();
        for (PhanCongDonViRequest list : request.getPhanCongDonVis()) {
            PhanCongDonVi phanCongDonVi = new PhanCongDonVi();
            phanCongDonVi.setCongViec(congViec);
            phanCongDonVi.setDonVi(donViRepository.findById(list.getDonVi().getMadonvi())
                    .orElseThrow(() -> new AppException(ErrorCode.DonVi_NOT_EXISTED)));
            phanCongDonVi.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                    .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
            phanCongDonVi.setThuchienchinh(list.isThuchienchinh());
            phanCongDonVis.add(phanCongDonVi);
            if(list.isThuchienchinh())
                madonvithuchien.append("{Mã Đơn Vị:").append(list.getDonVi().getMadonvi()).append("_Mã Quyền:").append(list.getQuyen().getMaquyen()).append("}_");
            else
                madonviphohop.append("{Mã Đơn Vị:").append(list.getDonVi().getMadonvi()).append("_Mã Quyền:").append(list.getQuyen().getMaquyen()).append("}_");
        }
            StringBuilder mabanlanhdao = new StringBuilder();
        // Xử lý phân công lãnh đạo từ request
        for (PhanCongLanhDaoRequest list : request.getPhanCongLanhDaos()) {
            PhanCongLanhDao phanCongLanhDao = new PhanCongLanhDao();
            phanCongLanhDao.setCongViec(congViec);
            phanCongLanhDao.setNhanVien(nhanVienRepository.findById(list.getNhanVien().getManhanvien())
                    .orElseThrow(() -> new AppException(ErrorCode.NhanVien_NOT_EXISTED)));
            phanCongLanhDao.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                    .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
            phanCongLanhDaos.add(phanCongLanhDao);
            mabanlanhdao.append("{Mã Nhân Viên:").append(list.getNhanVien().getManhanvien()).append("_Mã Quyền:").append(list.getQuyen().getMaquyen()).append("}_");
        }
        // Gán MucTieu, PhanCongDonVi và PhanCongLanhDao cho CongViec

        congViec.setMucTieu(mucTieu);
        congViec.setNhomMucTieu(nhomMucTieu);
        congViec.setPhanCongDonVis(phanCongDonVis);
        congViec.setPhanCongLanhDaos(phanCongLanhDaos);
        congViec.setThoigiantao(new Date());
        TrangThaiCongViec trangThaiCongViec = new TrangThaiCongViec();
        trangThaiCongViec.setMatrangthai(1);
        congViec.setTrangThaiCongViec(trangThaiCongViec);
        KetQuaCongViec ketQuaCongViec = new KetQuaCongViec();
        ketQuaCongViec.setMaketqua(1);
        congViec.setKetQuaCongViec(ketQuaCongViec);
        // Lưu CongViec và trả về CongViecResponse
        CongViec savedCongViec = giaoViecDonVịResponsitory.save(congViec);

        Log_GiaoViec logGiaoViec = new Log_GiaoViec();
        logGiaoViec.setLog_noidung("(Mã Mục Tiêu:" + mucTieu.getMamuctieu() + ")_(Mã Nhóm:" + nhomMucTieu.getManhom() +
                ")_(Ban Lanh Đạo:[" + mabanlanhdao + "])_(Đơn Vị Thực Hiện:[" + madonvithuchien + "])_(Đơn Vị Phối Hợp:[" +
                madonviphohop + "])_(Tên Công Việc:" + savedCongViec.getTencongviec() + ")_(Ngày Bắt Đầu:" + savedCongViec.getNgaybatdau() +
                ")_(Ngày Kết Thúc:" + savedCongViec.getNgayketthucdukien() + ")_(Trọng Số:" + savedCongViec.getTrongso() + ")_(Mô Tả:" + savedCongViec.getMota() + ")");
        logGiaoViec.setMa_congviec(savedCongViec.getMacongviec());
        logGiaoViec.setMa_nhanvien(savedCongViec.getMa_nguoitao());
        HanhDong hanhDong = new HanhDong();
        hanhDong.setMahanhdong(1);
        logGiaoViec.setHanhDong(hanhDong);
        logGiaoViecResponsitory.save(logGiaoViec);

        return congViecMapper.toCongViecResponse(savedCongViec);
    }

    @Transactional(rollbackFor = Exception.class)
    public CongViecResponse updateCongViecGiaoDonVi(String macongviec, CongViecRequest request) {
        // Lấy đối tượng CongViec từ cơ sở dữ liệu dựa trên mã công việc
        CongViec congViec = congViecResponsitory.findById(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));
        int trangthaicongviec = congViec.getTrangThaiCongViec().getMatrangthai();
        if(trangthaicongviec == 2)
            throw new AppException(ErrorCode.CongViec_Dang_ThucHien_ERRORUpdate);
        else if(trangthaicongviec == 3)
            throw new AppException(ErrorCode.CongViec_Da_ThucHien_ERRORUpdate);
        // Lấy MucTieu từ repository dựa trên mã mục tiêu từ request
        MucTieu mucTieu = mucTieuRepository.findById(request.getMucTieu().getMamuctieu())
                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));
        NhomMucTieu nhomMucTieu = nhomMucTieuResponsitory.findById(request.getNhomMucTieu().getManhom())
                .orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED));


        // Cập nhật thông tin công việc từ request
        congViec.setMucTieu(mucTieu);
        congViec.setNhomMucTieu(nhomMucTieu);
        congViec.setTencongviec(request.getTencongviec());
        congViec.setNgaybatdau(request.getNgaybatdau());
        congViec.setNgayketthucdukien(request.getNgayketthucdukien());
        congViec.setMacongvieccha(request.getMacongvieccha());
        congViec.setMota(request.getMota());

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

            Map<String,Object> trangthai = giaoViecDonVịResponsitory.findByTrangThaiCongViecDonVi(macongviec,madonvi);
            Object maTrangThaiObj = trangthai.get("ma_trangthai");
            if (maTrangThaiObj != null) {
                int maTrangThai = Integer.parseInt(maTrangThaiObj.toString());
                if (maTrangThai != 1) {
                    throw new AppException(ErrorCode.CongViec_Dang_ThucHien_ERROR, "Tên đơn vị: " + i.getDonVi().getTendonvi());
                }
            }
            long check = giaoViecDonVịResponsitory.countByCheckTruocKhiXoaDonVi(macongviec,madonvi);
            if(check > 0)
                throw new AppException(ErrorCode.XoaDonVi_PhanCong,"Đơn vị: " + i.getDonVi().getTendonvi());
            else
                giaoViecDonVịResponsitory.deleteDonViByMaCongViecAndMaDonVi(macongviec,madonvi);
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

        StringBuilder madonvithuchien = new StringBuilder();
        StringBuilder madonviphohop =  new StringBuilder();

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
            if(list.isThuchienchinh())
                madonvithuchien.append("{Mã Đơn Vị:").append(list.getDonVi().getMadonvi()).append("_Mã Quyền:").append(list.getQuyen().getMaquyen()).append("}_");
            else
                madonviphohop.append("{Mã Đơn Vị:").append(list.getDonVi().getMadonvi()).append("_Mã Quyền:").append(list.getQuyen().getMaquyen()).append("}_");
        }
        congViec.setPhanCongDonVis(phanCongDonVis);

        // Cập nhật tập hợp phân công lãnh đạo
        StringBuilder mabanlanhdao = new StringBuilder();
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
            mabanlanhdao.append("{Mã Nhân Viên:").append(list.getNhanVien().getManhanvien()).append("_Mã Quyền:").append(list.getQuyen().getMaquyen()).append("}_");
        }
        congViec.setPhanCongLanhDaos(phanCongLanhDaos);
        CongViec updateCongViec = congViecResponsitory.save(congViec);

        Log_GiaoViec logGiaoViec = new Log_GiaoViec();
        logGiaoViec.setLog_noidung("(Mã Mục Tiêu:" + mucTieu.getMamuctieu() + ")_(Mã Nhóm:" + nhomMucTieu.getManhom() +
                ")_(Ban Lanh Đạo:[" + mabanlanhdao + "])_(Đơn Vị Thực Hiện:[" + madonvithuchien + "])_(Đơn Vị Phối Hợp:[" +
                madonviphohop + "])_(Tên Công Việc:" + updateCongViec.getTencongviec() + ")_(Ngày Bắt Đầu:" + updateCongViec.getNgaybatdau() +
                ")_(Ngày Kết Thúc:" + updateCongViec.getNgayketthucdukien() + ")_(Trọng Số:" + updateCongViec.getTrongso() + ")_(Mô Tả:" + updateCongViec.getMota() + ")");
        logGiaoViec.setMa_congviec(updateCongViec.getMacongviec());
        logGiaoViec.setMa_nhanvien(updateCongViec.getMa_nguoitao());
        HanhDong hanhDong = new HanhDong();
        hanhDong.setMahanhdong(2);
        logGiaoViec.setHanhDong(hanhDong);
        logGiaoViecResponsitory.save(logGiaoViec);

        // Lưu CongViec và trả về CongViecResponse
        return congViecMapper.toCongViecResponse(updateCongViec);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean xoaCongViecGiaoDonVi(String macongviec,int manhanvien) {
        congViecResponsitory.findById(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

        Map<String, Object> trangthai = giaoViecDonVịResponsitory.findByTrangThaiDonViCongViecXoa(macongviec);

        Object matrangthaiObj = trangthai.get("ma_trangthai");
        if (matrangthaiObj != null) {
            int maTrangThai = Integer.parseInt(matrangthaiObj.toString());
            if (maTrangThai == 2) {
                throw new AppException(ErrorCode.CongViec_Dang_ThucHien_ERROR);

            }else if(maTrangThai == 3)
                throw new AppException(ErrorCode.CongViec_Da_ThucHien_ERROR);
        } else {
            throw new AppException(ErrorCode.TrangThai_NOT_EXISTED);
        }

        long check = congViecResponsitory.countByCheckTruocKhiXoa(macongviec);

        if(check > 0 )
            throw new AppException(ErrorCode.Error_Delete_CongViec);
        else{

            phanCongDonViRepository.deleteByMaCongViec(macongviec);
            phanCongLanhDaoRepository.deleteByMaCongViec(macongviec);
            congViecResponsitory.deleteCongViec(macongviec);
        }

        Log_GiaoViec logGiaoViec = new Log_GiaoViec();
        logGiaoViec.setLog_noidung("");
        logGiaoViec.setMa_congviec(macongviec);
        logGiaoViec.setMa_nhanvien(manhanvien);
        HanhDong hanhDong = new HanhDong();
        hanhDong.setMahanhdong(3);
        logGiaoViec.setHanhDong(hanhDong);
        logGiaoViecResponsitory.save(logGiaoViec);
        return  true;
    }
}
