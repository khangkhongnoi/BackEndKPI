package com.vttu.kpis.controller;


import com.vttu.kpis.dto.request.CheckDonViCoTaoCongViec;
import com.vttu.kpis.dto.request.CongViecRequest;
import com.vttu.kpis.dto.request.PhanCongDonViRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.PhanCongDonVi;
import com.vttu.kpis.service.CongViecService;
import com.vttu.kpis.service.PhanCongDonViService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/congviec")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CongViecController {

    CongViecService congViecService;
    PhanCongDonViService phanCongDonViService;

    @GetMapping
    ApiResponse<List<CongViecResponse>> getCongViecDonVi (@RequestParam("madonvi") int madonvi, @RequestParam("machucvu") int machucvu, @RequestParam("manhanvien") int manhanvien){


        if(machucvu == 5){

            return ApiResponse.<List<CongViecResponse>>builder()
                    .result(phanCongDonViService.getCongViecByMaNguoiTao(manhanvien))
                    .build();
        }

        else if(machucvu == 1){

            return  ApiResponse.<List<CongViecResponse>>builder()
                    .message("Bạn không có quyền truy cập")
                    .build();
        }

        else  {
            return ApiResponse.<List<CongViecResponse>>builder()
                    .result(phanCongDonViService.getCongViecTheoDonVi(madonvi, manhanvien))
                    .build();
        }
    }

    @PostMapping
    ResponseEntity<ApiResponse<CongViecResponse>> createCongViec(@RequestBody @Valid CongViecRequest request){

        ApiResponse<CongViecResponse> response = ApiResponse.<CongViecResponse>builder()
                .result(congViecService.createCongViec(request))
                .build();
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{macongviec}")
    ResponseEntity<ApiResponse<CongViecResponse>> updateCongViec(
            @PathVariable UUID macongviec,
            @RequestBody @Valid CongViecRequest request) {

        ApiResponse<CongViecResponse> response = ApiResponse.<CongViecResponse>builder()
                .result(congViecService.updateCongViec(macongviec, request))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{macongviec}")
   ApiResponse<CongViecResponse> getId(@PathVariable("macongviec") String macongviec){


        return ApiResponse.<CongViecResponse>builder()
                .result(congViecService.getCongViecByMaCongViec(macongviec))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable Long userId) {
        congViecService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }



}
