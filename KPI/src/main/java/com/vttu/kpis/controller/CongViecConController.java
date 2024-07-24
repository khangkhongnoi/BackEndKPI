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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/congvieccon")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CongViecConController {


    CongViecConService congViecConService;
    HttpServletRequest request;
    AuthenticationService authenticationService;

    @PostMapping("/nhanvien-bophan-donvi")
    ApiResponse<CongViecConNhanVienResponse> createCongViecCon (@RequestBody @Valid CongViecConNhanVienRequest congViecConNhanVienRequest){

        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<CongViecConNhanVienResponse>builder()
                        .result(congViecConService.createCongViecConNhanVienNhan(congViecConNhanVienRequest))
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
    @PostMapping("/bophan/nhanvien")
    ApiResponse<CongViecConNhanVienResponse> createCongViecConNhanVien (@RequestBody @Valid CongViecConNhanVienRequest congViecConNhanVienRequest){

        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<CongViecConNhanVienResponse>builder()
                        .result(congViecConService.createCongViecConBoPhanNhan(congViecConNhanVienRequest))
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
    @PutMapping("/bophan/nhanvien/{macongviec}")
    ApiResponse<CongViecConNhanVienResponse> updateCongViecConNhanVien (@RequestBody @Valid CongViecConNhanVienRequest congViecConNhanVienRequest, @PathVariable String macongviec){

        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<CongViecConNhanVienResponse>builder()
                        .result(congViecConService.updateCongViecConBoPhanNhan(macongviec,congViecConNhanVienRequest))
                        .code(HttpStatus.OK.value())
                        .message("Công việc con cập nhật thành công.")
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
    @PutMapping("/nhanvien/{macongviec}")
    ApiResponse<CongViecConNhanVienResponse> updateCongViecCon (@RequestBody @Valid CongViecConNhanVienRequest congViecConNhanVienRequest, @PathVariable String macongviec){

        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<CongViecConNhanVienResponse>builder()
                        .result(congViecConService.updateCongViecCon(macongviec,congViecConNhanVienRequest))
                        .code(HttpStatus.OK.value())
                        .message("Cập nhật công việc con thành công.")
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
    @PutMapping("/nhanviec/canhan/{macongviec}")
    ApiResponse<CongViecConNhanVienResponse> updateCongViecConnhanvieccanhan (@RequestBody @Valid CongViecConNhanVienRequest congViecConNhanVienRequest, @PathVariable String macongviec){

        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<CongViecConNhanVienResponse>builder()
                        .result(congViecConService.updateCongViecConNhanVienNhan(macongviec,congViecConNhanVienRequest))
                        .code(HttpStatus.OK.value())
                        .message("Cập nhật công việc con thành công.")
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
      @GetMapping("/chi-tiet-cong-viec-con/{macongvieccon}")
    ApiResponse<CongViecResponse> getChiTietCongViecConController(@PathVariable String macongvieccon){
          try {
              if(CheckToken.CheckHanToKen(request,authenticationService)){
                  return ApiResponse.<CongViecResponse>builder()
                          .result(congViecConService.getChiTietCongViecCon(macongvieccon))
                          .code(HttpStatus.OK.value())
                          .build();
              }else {
                  return ApiResponse.<CongViecResponse>builder()
                          .code(HttpStatus.UNAUTHORIZED.value())
                          .build();
              }
          }catch (ParseException | JOSEException e){
              e.printStackTrace();
              return ApiResponse.<CongViecResponse>builder()
                      .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                      .build();
          }
      }


}
