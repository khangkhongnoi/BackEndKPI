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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

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
}
