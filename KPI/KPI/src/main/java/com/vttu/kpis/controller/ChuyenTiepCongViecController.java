package com.vttu.kpis.controller;

import com.vttu.kpis.dto.request.ChuyenTiepCongViecRequest;
import com.vttu.kpis.dto.request.CongViecChuyenTiepResquest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.ChuyenTiepCongViecResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.dto.response.NhanVienResponse;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.service.ChuyenTiepCongViecService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/congviec/chuyentiepcongviec")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChuyenTiepCongViecController {

    ChuyenTiepCongViecService chuyenTiepCongViecService;

    @PostMapping("/{macongviec}")
    ApiResponse<ChuyenTiepCongViecResponse> createChuyenTiep(@PathVariable String macongviec, @RequestBody ChuyenTiepCongViecRequest request){


        if(macongviec.isEmpty())
            throw new AppException(ErrorCode.CongViec_NOT_EXISTED);
        return ApiResponse.<ChuyenTiepCongViecResponse>builder()
                .result(chuyenTiepCongViecService.createCongViecChuyenTiep(macongviec,request))
                .build();
    }

    @GetMapping("/{macongviec}")
    ApiResponse<List<NhanVienResponse>> getChuyenTiepCongViec (@PathVariable String macongviec){

        return  ApiResponse.<List<NhanVienResponse>>builder()
                .result(chuyenTiepCongViecService.getChuyenTiepByMaCongViec(macongviec))
                .build();
    }

    @GetMapping("nhanvien/{manhanvien}")
    ApiResponse<List<CongViecResponse>> getCongViecChuyenTiepByMaNhanVien(@PathVariable int manhanvien){

        return ApiResponse.<List<CongViecResponse>>builder()
                .result(chuyenTiepCongViecService.getCongViecChuyenTiepByMaNhanVien(manhanvien))
                .build();
    }


}
