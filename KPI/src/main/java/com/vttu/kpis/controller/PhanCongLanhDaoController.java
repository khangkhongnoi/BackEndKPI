package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.CheckBanLanhDaoTaoCongViecRequest;
import com.vttu.kpis.dto.request.ChuyenTiepCongViecRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.ChuyenTiepCongViecResponse;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.CongViecService;
import com.vttu.kpis.service.PhanCongDonViService;
import com.vttu.kpis.service.PhanCongLanhDaoService;
import com.vttu.kpis.utils.CheckToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/phanconglanhdao")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhanCongLanhDaoController {

    CongViecService congViecService;
    PhanCongLanhDaoService phanCongLanhDaoService;
    HttpServletRequest request;
    AuthenticationService authenticationService;

//    @PostMapping("/checkbanlanhdaotaocongviec")
//    ApiResponse<Boolean> checkbanlanhdao(@RequestBody CheckBanLanhDaoTaoCongViecRequest congViecRequest){
//        try{
//            if(CheckToken.CheckHanToKen(request,authenticationService)){
//                return ApiResponse.<Boolean>builder()
//                        .result(congViecService.CheckBanhLanhDaoTaoCongViec(congViecRequest.getMacongviec(),congViecRequest.getManhanvien()))
//                        .code(HttpStatus.OK.value())
//                        .build();
//            }else {
//                return ApiResponse.<Boolean>builder()
//                        .code(HttpStatus.UNAUTHORIZED.value())
//                        .build();
//            }
//        }catch (ParseException | JOSEException e){
//            e.printStackTrace();
//            return ApiResponse.<Boolean>builder()
//                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .build();
//        }
//
//    }


}
