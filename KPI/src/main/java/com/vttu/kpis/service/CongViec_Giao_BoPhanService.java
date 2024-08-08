package com.vttu.kpis.service;

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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CongViec_Giao_BoPhanService {

    CongViecResponsitory congViecResponsitory;
    CongViecMapper congViecMapper;
    DonViResponsitory donViRepository;
    MucTieuReoisitory mucTieuRepository;
    QuyenResponsitory quyenRepository;
    NhanVienResponsitory nhanVienRepository;
    PhanCongDonViRespository phanCongDonViRepository;
    PhanCongLanhDaoRepository phanCongLanhDaoRepository;
    NhomMucTieuResponsitory nhomMucTieuResponsitory;
    KetQuaCongViecResponsitory ketQuaCongViecResponsitory;
    PhanCongNhanVienResponsitory phanCongNhanVienResponsitory;
    XacNhanRespository xacNhanRespository;
    GiaHanResponsitory giaHanResponsitory;
    private final BoPhanResponsitory boPhanResponsitory;

    @Transactional(rollbackFor = Exception.class)
    public CongViecResponse createCongViec(CongViecRequest request){
        // Tạo đối tượng CongViec từ request
        CongViec congViec = congViecMapper.toCreateCongViec(request);

        // Lấy MucTieu từ repository dựa trên mã mục tiêu từ request
        MucTieu mucTieu = mucTieuRepository.findById(request.getMucTieu().getMamuctieu())
                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));

        nhomMucTieuResponsitory.findById(request.getNhomMucTieu().getManhom())
                .orElseThrow(() -> new AppException(ErrorCode.Nhom_NOT_EXISTED));

        // Khởi tạo tập hợp phân công đơn vị và phân công lãnh đạo
        Set<PhanCongBoPhan> phanCongBoPhans = new HashSet<>();
        Set<PhanCongLanhDao> phanCongLanhDaos = new HashSet<>();
        if(request.getPhanCongBoPhans().isEmpty())
            throw new AppException(ErrorCode.PhanCongBoPhanIsEmpty);
        // Xử lý phân công đơn vị từ request
        for(PhanCongBoPhanRequest list : request.getPhanCongBoPhans()){
            PhanCongBoPhan phanCongBoPhan = new PhanCongBoPhan();
            phanCongBoPhan.setCongViec(congViec);
            phanCongBoPhan.setBoPhan(boPhanResponsitory.findById(list.getBoPhan().getMabophan())
                    .orElseThrow(() -> new AppException(ErrorCode.BoPhan_NOT_EXISTED)));
            phanCongBoPhan.setQuyen(quyenRepository.findById(list.getQuyen().getMaquyen())
                    .orElseThrow(() -> new AppException(ErrorCode.Quyen_NOT_EXISTED)));
            phanCongBoPhan.setThuchienchinh(list.isThuchienchinh());
            phanCongBoPhans.add(phanCongBoPhan);

        }

        if(request.getPhanCongLanhDaos().isEmpty())
            throw new AppException(ErrorCode.PhanCongLanhDaoIsEmpty);
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
        congViec.setPhanCongBoPhans(phanCongBoPhans);
        congViec.setPhanCongLanhDaos(phanCongLanhDaos);
        congViec.setThoigiantao(ngayhientai);
        TrangThaiCongViec trangThaiCongViec = new TrangThaiCongViec();
        trangThaiCongViec.setMatrangthai(1);
        congViec.setTrangThaiCongViec(trangThaiCongViec);
        KetQuaCongViec ketQuaCongViec = new KetQuaCongViec();
        ketQuaCongViec.setMaketqua(1);
        congViec.setKetQuaCongViec(ketQuaCongViec);
        // Lưu CongViec và trả về CongViecResponse
        return congViecMapper.toCongViecResponse(congViecResponsitory.save(congViec));
    }

    public List<CongViecResponse> getCongViecGiaoBoPhanByMaNguoiTao (int ma_nguoitao){

        return congViecResponsitory.findByCV_Giao_BoPhan(ma_nguoitao)
                .stream()
                .map(congViec -> {
                    CongViecResponse response = congViecMapper.toCongViecResponse(congViec);
                    response.setNgayhientai(LocalDate.now());
                    return response;
                }).toList();

    }
}
