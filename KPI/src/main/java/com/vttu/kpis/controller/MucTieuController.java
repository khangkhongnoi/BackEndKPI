package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.MucTieuRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.MucTieuResponse;
import com.vttu.kpis.dto.response.NhomMucTieuResponse;
import com.vttu.kpis.entity.MucTieu;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.MucTieuService;
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

@RestController
@RequestMapping("/muctieu")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MucTieuController {

    MucTieuService mucTieuService;
    HttpServletRequest request;
    AuthenticationService authenticationService;

    @PostMapping
   ApiResponse<MucTieuResponse> createMucTieu(@RequestBody  @Valid MucTieuRequest mucTieuRequest){

        try {

            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<MucTieuResponse>builder()
                        .result(mucTieuService.createMucTieu(mucTieuRequest))
                        .message("Mục tiêu được tạo thành công")
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<MucTieuResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<MucTieuResponse>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }
    }
    @GetMapping
    ApiResponse<List<MucTieuResponse>> getAllMucTieu(){
        try {

            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<List<MucTieuResponse>>builder()
                        .result(mucTieuService.getAllMucTieu())
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<List<MucTieuResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<MucTieuResponse>>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }

    }

    @GetMapping("/{mamuctieu}")
    ApiResponse<MucTieu> getMucTieuByMaMucTieu(@PathVariable int mamuctieu){
        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<MucTieu>builder()
                        .result(mucTieuService.getMucTieuByMaMucTieu(mamuctieu))
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<MucTieu>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<MucTieu>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }

    }

    @PutMapping("/{mamuctieu}")
    ApiResponse<MucTieuResponse> updateMucTieu(@RequestBody  MucTieuRequest mucTieuRequest, @PathVariable int mamuctieu){

        try {

            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<MucTieuResponse>builder()
                        .result(mucTieuService.updateMucTieu(mamuctieu,mucTieuRequest))
                        .message("Mục tiêu được cập nhật thành công")
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<MucTieuResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<MucTieuResponse>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }
    }
}
