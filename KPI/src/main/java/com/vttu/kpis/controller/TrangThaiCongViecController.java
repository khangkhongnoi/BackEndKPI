package com.vttu.kpis.controller;

import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.entity.TrangThaiCongViec;
import com.vttu.kpis.service.TrangThaiCongViecService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trangthai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrangThaiCongViecController
{
    TrangThaiCongViecService trangThaiCongViecService;

    @GetMapping
    ApiResponse<List<TrangThaiCongViec>> getAllTrangThaiController (){
            return ApiResponse.<List<TrangThaiCongViec>>builder()
                    .result(trangThaiCongViecService.getAllService())
                    .build();
    }
}
