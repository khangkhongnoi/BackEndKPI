package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.BoPhanRequest;
import com.vttu.kpis.dto.request.DonViRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.BoPhanResponse;
import com.vttu.kpis.dto.response.DonViResponse;
import com.vttu.kpis.entity.BoPhan;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.BoPhanService;
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
@RequestMapping("/bophan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BoPhanController {

    BoPhanService boPhanService;
    HttpServletRequest request;
    AuthenticationService authenticationService;

    @GetMapping
    ApiResponse<List<BoPhanResponse>> getBoPhanByMaDonVi(@RequestParam("madonvi") int madonvi){

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<List<BoPhanResponse>>builder()
                        .result(boPhanService.getBoPhanByMaDonVi(madonvi))
                        .code(HttpStatus.OK.value())
                        .build();
            }else {
                return ApiResponse.<List<BoPhanResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        }catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<BoPhanResponse>>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }

    }

    @GetMapping("/list")
    ApiResponse<List<BoPhanResponse>> getBoPhanAll(){

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<List<BoPhanResponse>>builder()
                        .result(boPhanService.getAllBoPhan())
                        .code(HttpStatus.OK.value())
                        .build();
            }else {
                return ApiResponse.<List<BoPhanResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        }catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<BoPhanResponse>>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }

    }

    @PostMapping
    ApiResponse<BoPhanResponse> crateBoPhan(@RequestBody @Valid BoPhanRequest boPhanRequest) {

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<BoPhanResponse>builder()
                        .result(boPhanService.createBoPhan(boPhanRequest))
                        .code(HttpStatus.OK.value())
                        .message("Tạo bộ phận thành công")
                        .build();
            }else
            {
                return ApiResponse.<BoPhanResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }

        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<BoPhanResponse>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }

    }
    @GetMapping("/{mabophan}")
    ApiResponse<BoPhanResponse> getBoPhanAll(@PathVariable int mabophan){

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<BoPhanResponse>builder()
                        .result(boPhanService.getBoPhanById(mabophan))
                        .code(HttpStatus.OK.value())
                        .build();
            }else {
                return ApiResponse.<BoPhanResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        }catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<BoPhanResponse>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }

    }
    @PutMapping
    ApiResponse<BoPhanResponse> updateBoPhan(@RequestBody @Valid BoPhanRequest boPhanRequest) {

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<BoPhanResponse>builder()
                        .result(boPhanService.updateBoPhan(boPhanRequest))
                        .code(HttpStatus.OK.value())
                        .message("Cập nhật bộ phận thành công")
                        .build();
            }else
            {
                return ApiResponse.<BoPhanResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }

        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<BoPhanResponse>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }

    }
}
