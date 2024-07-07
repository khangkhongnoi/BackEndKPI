package com.vttu.kpis.controller;

import com.vttu.kpis.dto.request.CongViecConNhanVienRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecConNhanVienResponse;
import com.vttu.kpis.service.CongViecConService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/congviecconnhanvien")

public class CongVIecConController {

    @Autowired
    CongViecConService congViecConService;

    @PostMapping
    ApiResponse<CongViecConNhanVienResponse> createCongViecCon (@RequestBody @Valid CongViecConNhanVienRequest request){

        return ApiResponse.<CongViecConNhanVienResponse>builder()
                .result(congViecConService.createCongViec(request))
                .build();
    }
}
