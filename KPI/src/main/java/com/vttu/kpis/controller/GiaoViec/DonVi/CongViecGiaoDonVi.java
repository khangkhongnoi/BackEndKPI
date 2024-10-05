package com.vttu.kpis.controller.GiaoViec.DonVi;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.CongViecRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.CongViecService;
import com.vttu.kpis.service.PhanCongDonViService;
import com.vttu.kpis.service.giaoviec.GiaoViecDonViService;
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
@RequestMapping("/congviec/giaoviec/donvi")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

// giao việc dành cho Hiệu Trưởng và Ban Lãnh Đạo thực hiện tạo
public class CongViecGiaoDonVi {

    HttpServletRequest request;
    AuthenticationService authenticationService;
    PhanCongDonViService phanCongDonViService;
    GiaoViecDonViService giaoViecDonViService;
    CongViecService congViecService;

    @GetMapping
    ApiResponse<List<CongViecResponse>> getCongViecGiaoDonVi(@RequestParam("madonvi") int madonvi, @RequestParam("machucvu") int machucvu, @RequestParam("manhanvien") int manhanvien) {

        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                // 5 là hiệu trưởng, 6 là ban lãnh đạo
                if (machucvu == 5 || machucvu == 6) {

                    return ApiResponse.<List<CongViecResponse>>builder()
                            .result(phanCongDonViService.getCongViecByMaNguoiTao(manhanvien))
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

    @PostMapping
    ApiResponse<CongViecResponse> createCongViecGiaoDonVi(@RequestBody @Valid CongViecRequest congViecRequest) {

        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<CongViecResponse>builder()
                        .result(giaoViecDonViService.createCongViecGiaoDonVi(congViecRequest))
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

    @PutMapping("/{macongviec}")
    ApiResponse<CongViecResponse> updateCongViec(
            @PathVariable String macongviec,
            @RequestBody @Valid CongViecRequest congViecRequest) {

        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<CongViecResponse>builder()
                        .result(giaoViecDonViService.updateCongViecGiaoDonVi(macongviec, congViecRequest))
                        .message("Công việc được cập nhật thành công")
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

    @DeleteMapping("/xoa-cong-viec-giao-don-vi/{macongviec}")
    ApiResponse<Boolean> xoaCongViecGiaoDonVi(@PathVariable String macongviec, @RequestParam int manhanvien) {
        try {
            if (CheckToken.CheckHanToKen(request, authenticationService)) {
                return ApiResponse.<Boolean>builder()
                        .result(giaoViecDonViService.xoaCongViecGiaoDonVi(macongviec,manhanvien))
                        .code(HttpStatus.OK.value())
                        .message("Xóa công việc thành công")
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


}
