package com.vttu.kpis.controller;


import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.NhomMucTieuRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.NhomMucTieuResponse;
import com.vttu.kpis.entity.NhomMucTieu;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.NhomMucTieuService;
import com.vttu.kpis.utils.CheckToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/nhommuctieu")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NhomMucTieuController {

    NhomMucTieuService nhomMucTieuService;
    HttpServletRequest request;
    AuthenticationService authenticationService;

    @PostMapping
    ApiResponse<NhomMucTieuResponse> createNhomMucTieu(@RequestBody @Valid NhomMucTieuRequest nhomMucTieuRequest){

        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<NhomMucTieuResponse>builder()
                        .result(nhomMucTieuService.createNhomMucTieu(nhomMucTieuRequest))
                        .message("Nhóm mục tiêu được tạo thành công")
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<NhomMucTieuResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }

        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<NhomMucTieuResponse>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }

    }
    @GetMapping
     ApiResponse<List<NhomMucTieuResponse>> getNhomMucTieu(){

        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<List<NhomMucTieuResponse>>builder()
                        .result(nhomMucTieuService.getNhomMucTieu())
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<List<NhomMucTieuResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
        }catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<NhomMucTieuResponse>>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }
    }

    @GetMapping("/{manhom}")
     ApiResponse<NhomMucTieuResponse> getNhom (@PathVariable("manhom") int manhom ){
            try {
                if(CheckToken.CheckHanToKen(request,authenticationService)){
                    return ApiResponse.<NhomMucTieuResponse>builder()
                            .result(nhomMucTieuService.getNhom(manhom))
                            .code(HttpStatus.OK.value())
                            .build();
                }else {
                    return ApiResponse.<NhomMucTieuResponse>builder()
                            .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                            .build();
                }
            }catch (ParseException | JOSEException e) {
                e.printStackTrace();
                return ApiResponse.<NhomMucTieuResponse>builder()
                        .message("Internal Server Error")
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                        .build();
            }
    }

    @PutMapping("/{manhom}")
    ApiResponse<NhomMucTieuResponse> updatenhom(@PathVariable("manhom") int manhom, @RequestBody NhomMucTieuRequest nhomMucTieuRequest){
        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<NhomMucTieuResponse>builder()
                        .result(nhomMucTieuService.updateNhom(manhom,nhomMucTieuRequest))
                        .message("Nhóm mục tiêu được cập nhật thành công")
                        .code(HttpStatus.OK.value())
                        .build();
            }else {
                return ApiResponse.<NhomMucTieuResponse>builder()
                        .result(null)
                        .message("Unauthorized")
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
        }catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return ApiResponse.<NhomMucTieuResponse>builder()
                    .result(null)
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }

    }
}
