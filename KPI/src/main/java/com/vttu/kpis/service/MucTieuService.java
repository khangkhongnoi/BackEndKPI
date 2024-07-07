package com.vttu.kpis.service;

import com.vttu.kpis.dto.request.MucTieuRequest;
import com.vttu.kpis.dto.response.MucTieuResponse;
import com.vttu.kpis.entity.MucTieu;
import com.vttu.kpis.entity.NhomMucTieu;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.MucTieuMapper;
import com.vttu.kpis.responsitory.MucTieuReoisitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MucTieuService {

    MucTieuReoisitory mucTieuReoisitory;

    MucTieuMapper mucTieuMapper;

    public MucTieuResponse createMucTieu(MucTieuRequest request){

        if(mucTieuReoisitory.existsByTenmuctieu(request.getTenmuctieu())) throw new AppException(ErrorCode.MucTieu_EXISTED);

        MucTieu mucTieu = mucTieuMapper.toMucTieu(request);

        Set<NhomMucTieu> listnhom = request.getNhomMucTieus();

        Set<NhomMucTieu> nhomMucTieuSet = new HashSet<>();
        for (NhomMucTieu item : listnhom){
            NhomMucTieu nhomMucTieu = new NhomMucTieu();
            nhomMucTieu.setManhom(item.getManhom()); // Thiết lập mã nhóm từ chuỗi item
            nhomMucTieuSet.add(nhomMucTieu); // Thêm đối tượng NhomMucTieu vào tập hợp
            System.out.println(item.getManhom());

        }
        mucTieu.setNhomMucTieus(nhomMucTieuSet);


        return mucTieuMapper.toMucTieuResponse(mucTieuReoisitory.save(mucTieu));
    }

    public MucTieuResponse updateMucTieu(Integer id, MucTieuRequest request) {
        // Tìm mục tiêu cần cập nhật
        MucTieu mucTieu = mucTieuReoisitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_FOUND));

        // Kiểm tra nếu tên mục tiêu đã tồn tại trong cơ sở dữ liệu (ngoại trừ mục tiêu hiện tại)
        if (mucTieuReoisitory.existsByTenmuctieuAndMamuctieuNot(request.getTenmuctieu(), id)) {
            throw new AppException(ErrorCode.MucTieu_EXISTED);
        }

        // Cập nhật các thuộc tính của mục tiêu
        mucTieu.setTenmuctieu(request.getTenmuctieu());
        mucTieu.setNgaybatdau(request.getNgaybatdau());
        mucTieu.setNgayketthuc(request.getNgayketthuc());
        mucTieu.setMota(request.getMota());
        // Thêm các thuộc tính cần cập nhật khác nếu có

        // Xóa tất cả các nhóm mục tiêu hiện tại liên quan đến mục tiêu
        mucTieuReoisitory.deleteAllNhomMucTieuByMucTieuId(id);

        // Thêm lại các nhóm mục tiêu từ yêu cầu
        Set<NhomMucTieu> listnhom = request.getNhomMucTieus();
        Set<NhomMucTieu> nhomMucTieuSet = new HashSet<>();
        for (NhomMucTieu item : listnhom) {
            NhomMucTieu nhomMucTieu = new NhomMucTieu();
            nhomMucTieu.setManhom(item.getManhom());
            nhomMucTieuSet.add(nhomMucTieu);
            System.out.println(item.getManhom());
        }
        mucTieu.setNhomMucTieus(nhomMucTieuSet);

        // Lưu lại mục tiêu đã được cập nhật
        return mucTieuMapper.toMucTieuResponse(mucTieuReoisitory.save(mucTieu));
    }

    public List<MucTieuResponse> getAllMucTieu(){
        return mucTieuReoisitory.findAll(Sort.by(Sort.Direction.DESC, "mamuctieu")).stream().map(mucTieuMapper::toMucTieuResponse).toList();
    }

    public MucTieu getMucTieuByMaMucTieu(int mamuctieu){

        return  mucTieuReoisitory.findById(mamuctieu).orElseThrow(() -> new AppException(ErrorCode.MucTieu_NOT_EXISTED));
    }


}


