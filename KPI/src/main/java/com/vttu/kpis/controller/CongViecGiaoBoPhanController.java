package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.CongViecRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.service.*;
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
@RequestMapping("/congviec_giao_bophan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CongViecGiaoBoPhanController {

   CongViec_Giao_BoPhanService congViec_Giao_BoPhanService;
    PhanCongDonViService phanCongDonViService;
    HttpServletRequest request;
    AuthenticationService authenticationService;
    PhanCongBoPhanService phanCongBoPhanService;
    PhanCongNhanVienService phanCongNhanVienService;
    @PostMapping
    ApiResponse<CongViecResponse> createCongViec(@RequestBody @Valid CongViecRequest congViecRequest) {

        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<CongViecResponse>builder()
                        .result(congViec_Giao_BoPhanService.createCongViec(congViecRequest))
                        .message("Công việc được tạo thành công")
                        .code(HttpStatus.OK.value())
                        .build();
            } else {
                return ApiResponse.<CongViecResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return ApiResponse.<CongViecResponse>builder()
                    .message("Thêm không thành công.Internal Server Error!")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }
    }

    @GetMapping
    ApiResponse<List<CongViecResponse>> getCongViecDonVi(@RequestParam("madonvi") int madonvi, @RequestParam("machucvu") int machucvu, @RequestParam("manhanvien") int manhanvien) {

        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {

                if (machucvu == 5 || machucvu == 6) {

                    return ApiResponse.<List<CongViecResponse>>builder()
                            .result(congViec_Giao_BoPhanService.getCongViecGiaoBoPhanByMaNguoiTao(manhanvien))
                            .code(HttpStatus.OK.value())
                            .build();
                } else if (machucvu == 1) {

                    return ApiResponse.<List<CongViecResponse>>builder()
                            .message("Bạn không có quyền truy cập")
                            .code(HttpStatus.FORBIDDEN.value())
                            .build();
                } else {
                    return ApiResponse.<List<CongViecResponse>>builder()
                            .result(phanCongDonViService.getCongViecTheoDonVi(madonvi))
                            .code(HttpStatus.OK.value())
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
}
