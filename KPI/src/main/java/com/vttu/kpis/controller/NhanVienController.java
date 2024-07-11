package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.DonViResponse;
import com.vttu.kpis.dto.response.NhanVienResponse;
import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.NhanVienService;
import com.vttu.kpis.utils.CheckToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/nhanvien")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NhanVienController {

    NhanVienService nhanVienService;
    HttpServletRequest request;
    AuthenticationService authenticationService;

    @GetMapping("/{manhanvien}")
    ApiResponse<NhanVien> getall(@PathVariable int manhanvien){

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<NhanVien>builder()
                        .result(nhanVienService.getNhanVien(manhanvien))
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<NhanVien>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
    } catch (ParseException | JOSEException e){
        e.printStackTrace();
        return ApiResponse.<NhanVien>builder()
                .message("Internal Server Error")
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                .build();
    }

    }
    // lấy thông tin nhân viên trong đơn vị lãnh đạo
    @GetMapping("/banlanhdao/{machucvu}")
    ApiResponse<List<NhanVienResponse>> getBanLanhDao (@PathVariable int machucvu){

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<List<NhanVienResponse>>builder()
                        .result(nhanVienService.getBanLanhDao(machucvu))
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<List<NhanVienResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<NhanVienResponse>>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }

    }
    @GetMapping("/nhan-vien-theo-don-vi/{madonvi}")
    ApiResponse<List<NhanVienResponse>> getNhanVienTheoDonVi (@PathVariable int madonvi){
        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<List<NhanVienResponse>>builder()
                        .result(nhanVienService.getNhanVienDonVi(madonvi))
                        .code(HttpStatus.OK.value())
                        .build();
            }else {
                return ApiResponse.<List<NhanVienResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        }catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<NhanVienResponse>>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

}
