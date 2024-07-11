package com.vttu.kpis.controller;


import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.QuyenResponse;
import com.vttu.kpis.service.QuyenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/quyen")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuyenController {

    QuyenService quyenService;

    @GetMapping
    ApiResponse<List<QuyenResponse>> getAll(){
        return ApiResponse.<List<QuyenResponse>>builder()
                .result(quyenService.getAll())
                .build();
    }
}
