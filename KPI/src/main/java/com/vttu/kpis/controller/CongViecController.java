package com.vttu.kpis.controller;


import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.CheckDonViCoTaoCongViec;
import com.vttu.kpis.dto.request.CongViecRequest;
import com.vttu.kpis.dto.request.PhanCongDonViRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.MucTieu;
import com.vttu.kpis.entity.PhanCongDonVi;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.CongViecService;
import com.vttu.kpis.service.PhanCongDonViService;
import com.vttu.kpis.utils.CheckToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/congviec")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CongViecController {

    CongViecService congViecService;
    PhanCongDonViService phanCongDonViService;
    HttpServletRequest request;
    AuthenticationService authenticationService;
    @GetMapping
    ApiResponse<List<CongViecResponse>> getCongViecDonVi (@RequestParam("madonvi") int madonvi, @RequestParam("machucvu") int machucvu, @RequestParam("manhanvien") int manhanvien){

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){

                if(machucvu == 5 || machucvu == 3){

                    return ApiResponse.<List<CongViecResponse>>builder()
                            .result(phanCongDonViService.getCongViecByMaNguoiTao(manhanvien))
                            .code(HttpStatus.OK.value())
                            .build();
                }

                else if(machucvu == 1){

                    return  ApiResponse.<List<CongViecResponse>>builder()
                            .message("Bạn không có quyền truy cập")
                            .code(HttpStatus.FORBIDDEN.value())
                            .build();
                }

                else  {
                    return ApiResponse.<List<CongViecResponse>>builder()
                            .result(phanCongDonViService.getCongViecTheoDonVi(madonvi, manhanvien))
                            .code(HttpStatus.OK.value())
                            .build();
                }
            }else  {
                return ApiResponse.<List<CongViecResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        }catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<CongViecResponse>>builder()
                    .message("Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    @GetMapping("/duocgiao")
    ApiResponse<List<CongViecResponse>> getCongViecDonViDuocGiao (@RequestParam("madonvi") int madonvi, @RequestParam("machucvu") int machucvu, @RequestParam("manhanvien") int manhanvien){

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){

                if(machucvu == 5){

                    return ApiResponse.<List<CongViecResponse>>builder()
                            .result(phanCongDonViService.getCongViecByMaNguoiTao(manhanvien))
                            .code(HttpStatus.OK.value())
                            .build();
                }

                else if(machucvu == 1){

                    return  ApiResponse.<List<CongViecResponse>>builder()
                            .message("Bạn không có quyền truy cập")
                            .code(HttpStatus.FORBIDDEN.value())
                            .build();
                }

                else  {
                    return ApiResponse.<List<CongViecResponse>>builder()
                            .result(phanCongDonViService.getCongViecTheoDonVi(madonvi, manhanvien))
                            .code(HttpStatus.OK.value())
                            .build();
                }
            }else  {
                return ApiResponse.<List<CongViecResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        }catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<CongViecResponse>>builder()
                    .message("Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    @PostMapping
  ApiResponse<CongViecResponse> createCongViec(@RequestBody @Valid CongViecRequest congViecRequest){

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<CongViecResponse>builder()
                        .result(congViecService.createCongViec(congViecRequest))
                        .message("Công việc được tạo thành công")
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<CongViecResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<CongViecResponse>builder()
                    .message("Thêm không thành công.Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }
    }
    @PutMapping("/{macongviec}")
   ApiResponse<CongViecResponse> updateCongViec(
            @PathVariable UUID macongviec,
            @RequestBody @Valid CongViecRequest congViecRequest) {

        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<CongViecResponse>builder()
                        .result(congViecService.updateCongViec(macongviec, congViecRequest))
                        .message("Công việc được cập nhật thành công")
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
                    .message("Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }

    }

    @GetMapping("/{macongviec}")
   ApiResponse<CongViecResponse> getId(@PathVariable("macongviec") String macongviec){

        try{
            if (CheckToken.CheckHanToKen(request,authenticationService))
            {
                return ApiResponse.<CongViecResponse>builder()
                        .result(congViecService.getCongViecByMaCongViec(macongviec))
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
                    .message("Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable Long userId) {
        congViecService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }



}
