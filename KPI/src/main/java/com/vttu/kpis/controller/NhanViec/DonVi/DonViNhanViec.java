package com.vttu.kpis.controller.NhanViec.DonVi;


import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.CongViecService;
import com.vttu.kpis.service.nhanviec.NhanViecDonViServer;
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
@RequestMapping("/congviec/nhanviec/donvi")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DonViNhanViec {

    HttpServletRequest request;
    AuthenticationService authenticationService;
    NhanViecDonViServer nhanViecDonViServer;
    CongViecService congViecService;

    @GetMapping
    ApiResponse<List<CongViecResponse>> getCongViecDonViNhan(@RequestParam("madonvi") int madonvi, @RequestParam("machucvu") int machucvu, @RequestParam("manhanvien") int manhanvien) {

        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {

                if (machucvu == 3) {

                    return ApiResponse.<List<CongViecResponse>>builder()
                            .result(nhanViecDonViServer.getCongViecNhanTheoDonVi(madonvi))
                            .code(HttpStatus.OK.value())
                            .build();
                } else if (machucvu == 6) {
                    return ApiResponse.<List<CongViecResponse>>builder()
                            .result(congViecService.getCongViecBanLanhDaoService(manhanvien))
                            .code(HttpStatus.OK.value())
                            .build();
                } else {
                    return ApiResponse.<List<CongViecResponse>>builder()
                            .code(HttpStatus.UNAUTHORIZED.value())
                            .build();
                }
            } else {
                return ApiResponse.<List<CongViecResponse>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return ApiResponse.<List<CongViecResponse>>builder()
                    .message("Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
    @GetMapping("/{macongviec}")
    ApiResponse<CongViecResponse> getId(@PathVariable("macongviec") String macongviec) {

        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<CongViecResponse>builder()
                        .result(congViecService.getCongViecByMaCongViec(macongviec))
                        .code(HttpStatus.OK.value())
                        .build();
            } else {
                return ApiResponse.<CongViecResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return ApiResponse.<CongViecResponse>builder()
                    .message("Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
}
