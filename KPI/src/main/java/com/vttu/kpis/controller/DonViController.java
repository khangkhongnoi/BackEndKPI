package com.vttu.kpis.controller;


import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.DonViResponse;
import com.vttu.kpis.dto.response.NhomMucTieuResponse;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.DonViService;
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
@RequestMapping("/donvi")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DonViController {

    DonViService donViService;
    HttpServletRequest request;
    AuthenticationService authenticationService;
    @GetMapping
    ApiResponse<List<DonViResponse>> getAll() {

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<List<DonViResponse>>builder()
                        .result(donViService.getAllDonVi())
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<List<DonViResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }

     } catch (ParseException | JOSEException e){
        e.printStackTrace();
        return ApiResponse.<List<DonViResponse>>builder()
                .message("Internal Server Error")
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                .build();
    }

    }

    @GetMapping("donviphoihop")
    ApiResponse<List<DonViResponse>> getDonViPhoiHopThucHien(@RequestParam("madonvi") int madonvi){

        try {

            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<List<DonViResponse>>builder()
                        .result(donViService.getDonViPhoiHopThucHien(madonvi))
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<List<DonViResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }

        }catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<DonViResponse>>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }

    }
}
