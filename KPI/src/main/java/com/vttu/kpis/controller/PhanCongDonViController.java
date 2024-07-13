package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.CheckDonViCoTaoCongViec;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.dto.response.PhanCongDonViResponse;
import com.vttu.kpis.entity.DonVi;
import com.vttu.kpis.entity.PhanCongDonVi;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.CongViecService;
import com.vttu.kpis.service.PhanCongDonViService;
import com.vttu.kpis.utils.CheckToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/phancongdonvi")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhanCongDonViController {

    CongViecService congViecService;
    PhanCongDonViService phanCongDonViService;
    HttpServletRequest request;
    AuthenticationService authenticationService;
//    @PostMapping("/checkdonvitaocongviec")
//    ApiResponse<Boolean> check (@RequestBody CheckDonViCoTaoCongViec checkDonViCoTaoCongViec){
//
//        try{
//            if(CheckToken.CheckHanToKen(request, authenticationService)){
//                return ApiResponse.<Boolean>builder()
//                        .result(congViecService.CheckDonVi(checkDonViCoTaoCongViec.getMacongviec(),checkDonViCoTaoCongViec.getMadonvi()))
//                        .build();
//            }else {
//                return ApiResponse.<Boolean>builder()
//                        .code(HttpStatus.UNAUTHORIZED.value())
//                        .build();
//            }
//        }catch (ParseException | JOSEException e){
//            return ApiResponse.<Boolean>builder()
//                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
//                    .build();
//        }
//    }

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
