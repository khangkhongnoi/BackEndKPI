package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.CongViecConNhanVienRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecConNhanVienResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.CongViecConService;
import com.vttu.kpis.utils.CheckToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/congviecconnhanvien")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CongVIecConController {


    CongViecConService congViecConService;
    HttpServletRequest request;
    AuthenticationService authenticationService;

    @PostMapping
    ApiResponse<CongViecConNhanVienResponse> createCongViecCon (@RequestBody @Valid CongViecConNhanVienRequest congViecConNhanVienRequest){

        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<CongViecConNhanVienResponse>builder()
                        .result(congViecConService.createCongViec(congViecConNhanVienRequest))
                        .code(HttpStatus.OK.value())
                        .message("Công việc con được tạo thành công.")
                        .build();
            }else{
                return ApiResponse.<CongViecConNhanVienResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        }catch (ParseException | JOSEException e){{
            e.printStackTrace();
            return ApiResponse.<CongViecConNhanVienResponse>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }}


    }
    @GetMapping("/danh-sanh-cong-viec-con/{macongvieccha}")
    ApiResponse<List<CongViecResponse>> getCongViecConTheoMaCongViecChaController(@PathVariable String macongvieccha){
        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<List<CongViecResponse>>builder()
                        .result(congViecConService.getCongViecConTheoMaCongViecChaService(macongvieccha))
                        .code(HttpStatus.OK.value())
                        .build();
            }else {
                return ApiResponse.<List<CongViecResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        }catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<CongViecResponse>>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
}
