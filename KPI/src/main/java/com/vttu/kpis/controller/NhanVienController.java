package com.vttu.kpis.controller;

import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.NhanVienResponse;
import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.service.NhanVienService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/nhanvien")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NhanVienController {

    NhanVienService nhanVienService;

    @GetMapping("/{manhanvien}")
    ApiResponse<NhanVien> getall(@PathVariable int manhanvien){

        return ApiResponse.<NhanVien>builder()
                .result(nhanVienService.getNhanVien(manhanvien))
                .build();
    }
    // lấy thông tin nhân viên trong đơn vị lãnh đạo
    @GetMapping("/banlanhdao/{madonvi}")
    ApiResponse<List<NhanVienResponse>> getBanLanhDao (@PathVariable int madonvi){
        return ApiResponse.<List<NhanVienResponse>>builder()
                .result(nhanVienService.getBanLanhDao(madonvi))
                .build();
    }
}
