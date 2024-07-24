package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.XacNhan;
import com.vttu.kpis.responsitory.XacNhanRespository;
import com.vttu.kpis.service.*;
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
@RequestMapping("congviec/xacnhan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class XacNhanController {

    CongViecService congViecService;
    HttpServletRequest request;
    AuthenticationService authenticationService;
    XacNhanRespository xacNhanRespository;

    @PutMapping("yeu-cau-xac-nhan/{macongviec}")
    ApiResponse<Boolean> yeuCauXacNhanHoanThanhCongViec(@PathVariable String macongviec) {
        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<Boolean>builder()
                        .result(congViecService.YeuCauXacNhan(macongviec))
                        .code(HttpStatus.OK.value())
                        .message("Đã gửi yêu cầu thành công")
                        .build();
            } else {
                return ApiResponse.<Boolean>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return ApiResponse.<Boolean>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
    @PutMapping("xac-nhan-yeu-cau/{macongviec}")
    ApiResponse<Boolean> XacNhanHoanThanhCongViec(@PathVariable String macongviec, @RequestParam boolean xacnhan, @RequestParam String noidung) {
        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<Boolean>builder()
                        .result(congViecService.XacNhanYeuCauHoanThanhCongViec(macongviec,xacnhan,noidung))
                        .code(HttpStatus.OK.value())
                        .message("Xác nhận yêu cầu thành công")
                        .build();
            } else {
                return ApiResponse.<Boolean>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return ApiResponse.<Boolean>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    @GetMapping("/lich-su-xac-nhan/{macongviec}")
    ApiResponse<List<XacNhan>> lich_su_xac_nhan(@PathVariable String macongviec){
        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<List<XacNhan>>builder()
                        .result(xacNhanRespository.findXacNhanByMaCongViec(macongviec))
                        .code(HttpStatus.OK.value())
                        .build();
            } else {
                return ApiResponse.<List<XacNhan>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return ApiResponse.<List<XacNhan>>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
}
