package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.GiaHanRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.GiaHan;
import com.vttu.kpis.responsitory.GiaHanResponsitory;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.CongViecService;
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
@RequestMapping("congviec/giahan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiaHanController {
    CongViecService congViecService;
    HttpServletRequest request;
    AuthenticationService authenticationService;
    private final GiaHanResponsitory giaHanResponsitory;

    @PutMapping("/yeu-cau-gia-han-cong-viec")
    ApiResponse<Boolean> yeuCauGiaHanCongViec(@RequestBody @Valid GiaHanRequest giaHanRequest) {
        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<Boolean>builder()
                        .result(congViecService.YeuCauGiaHanCongViec(giaHanRequest))
                        .code(HttpStatus.OK.value())
                        .message("Đã gửi yêu cầu gia hạn thành công")
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

    @GetMapping("/get-xac-nhan-gia-han/{macongviec}")
    ApiResponse<GiaHan> getXacNhanGiaHan(@PathVariable("macongviec") String macongviec) {

        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<GiaHan>builder()
                        .result(giaHanResponsitory.findGiaHanByThoigiantaoMaxAndMaCongViec(macongviec))
                        .code(HttpStatus.OK.value())
                        .build();
            } else {
                return ApiResponse.<GiaHan>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return ApiResponse.<GiaHan>builder()
                    .message("Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
    @PostMapping("/xac-nhan-gia-han")
    ApiResponse<Boolean> XacNhanGiaHan(@RequestBody GiaHanRequest giaHanRequest, @RequestParam("xacnhan") boolean xacnhan, @RequestParam("lydokhonggiahan") String lydokhonggiahan) {

        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<Boolean>builder()
                        .result(congViecService.XacNhanYeuCauGiaHanCongViec(giaHanRequest,xacnhan,lydokhonggiahan))
                        .code(HttpStatus.OK.value())
                        .message("Xác nhận thành công")
                        .build();
            } else {
                return ApiResponse.<Boolean>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return ApiResponse.<Boolean>builder()
                    .message("Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
    @GetMapping("/lich-su-gia-han/{macongviec}")
    ApiResponse<List<GiaHan>> getLichSuGiaHan(@PathVariable("macongviec") String macongviec) {

        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<List<GiaHan>>builder()
                        .result(giaHanResponsitory.findGiaHanByMaCongViec(macongviec))
                        .code(HttpStatus.OK.value())
                        .build();
            } else {
                return ApiResponse.<List<GiaHan>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return ApiResponse.<List<GiaHan>>builder()
                    .message("Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
}
