package com.vttu.kpis.controller;

import com.vttu.kpis.dto.request.CheckBanLanhDaoTaoCongViecRequest;
import com.vttu.kpis.dto.request.ChuyenTiepCongViecRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.ChuyenTiepCongViecResponse;
import com.vttu.kpis.service.CongViecService;
import com.vttu.kpis.service.PhanCongDonViService;
import com.vttu.kpis.service.PhanCongLanhDaoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/phanconglanhdao")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhanCongLanhDaoController {

    CongViecService congViecService;
    PhanCongLanhDaoService phanCongLanhDaoService;

    @PostMapping("/checkbanlanhdaotaocongviec")
    ApiResponse<Boolean> checkbanlanhdao(@RequestBody CheckBanLanhDaoTaoCongViecRequest request){
        return ApiResponse.<Boolean>builder()
                .result(congViecService.CheckBanhLanhDaoTaoCongViec(request.getMacongviec(),request.getManhanvien()))
                .build();
    }


}
