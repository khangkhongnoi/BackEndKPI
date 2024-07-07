package com.vttu.kpis.controller;

import com.vttu.kpis.dto.request.CheckDonViCoTaoCongViec;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.dto.response.PhanCongDonViResponse;
import com.vttu.kpis.entity.DonVi;
import com.vttu.kpis.entity.PhanCongDonVi;
import com.vttu.kpis.service.CongViecService;
import com.vttu.kpis.service.PhanCongDonViService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/phancongdonvi")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhanCongDonViController {

    CongViecService congViecService;
    PhanCongDonViService phanCongDonViService;

    @PostMapping("/checkdonvitaocongviec")
    ApiResponse<Boolean> check (@RequestBody CheckDonViCoTaoCongViec checkDonViCoTaoCongViec){

        return ApiResponse.<Boolean>builder()
                .result(congViecService.CheckDonVi(checkDonViCoTaoCongViec.getMacongviec(),checkDonViCoTaoCongViec.getMadonvi()))
                .build();
    }

//    @GetMapping("/{madonvi}")
//    ApiResponse<List<PhanCongDonViResponse>> getDonVi (@PathVariable int madonvi){
//        return ApiResponse.<List<PhanCongDonViResponse>>builder()
//                .result(phanCongDonViService.listPhanConDonVi(madonvi))
//                .build();
//    }

    @GetMapping
    ApiResponse<List<CongViecResponse>> getDonVi ( @RequestParam("madonvi") int madonvi, @RequestParam("machucvu") int machucvu, @RequestParam("manhanvien") int manhanvien){

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

}
