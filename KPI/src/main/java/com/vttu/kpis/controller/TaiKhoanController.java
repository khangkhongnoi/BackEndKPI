package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.LoginRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.ThongTinLoginResponse;
import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.entity.NhanVien_ChucVu;
import com.vttu.kpis.entity.TaiKhoan;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.NhanVienService;
import com.vttu.kpis.service.NhanVien_ChucVuService;
import com.vttu.kpis.service.TaiKhoanService;
import com.vttu.kpis.utils.CheckToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/taikhoan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaiKhoanController {

    TaiKhoanService taiKhoanService;
    NhanVienService nhanVienService;
    NhanVien_ChucVuService nhanVienChucVuService;
    HttpServletRequest request;
    AuthenticationService authenticationService;

    @GetMapping("list")
    ApiResponse<List<Map<String,Object>>> listtaikhoan() {

        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                return ApiResponse.<List<Map<String,Object>>>builder()
                        .result(taiKhoanService.listtaikhoanServer())
                        .code(HttpStatus.OK.value())
                        .build();
            }else
            {
                return ApiResponse.<List<Map<String,Object>>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }

        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<Map<String,Object>>>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }

    }

    @GetMapping("/{mataikhoan}")
    ApiResponse<List<NhanVien_ChucVu>> gettaikhoan(@PathVariable String mataikhoan){
        return ApiResponse.<List<NhanVien_ChucVu>>builder()
                .result(nhanVienChucVuService.getAllNhanVienChucVu(mataikhoan))
                .build();
    }
    @PostMapping("/checkdangnhap")
    ApiResponse<Boolean> check (@RequestBody LoginRequest request){

        return ApiResponse.<Boolean>builder()
                .result(taiKhoanService.checkDangNhap(request))
                .build();
    }

    @GetMapping("/thongtinnhanvien")
    ApiResponse<ThongTinLoginResponse> getTT (HttpServletResponse response, @RequestParam("manhanvien") int manhanvien, @RequestParam("machucvu") int machucvu){
            System.out.println(manhanvien + "=" + machucvu);
            

        Cookie cookie = new Cookie("userInfo", "nhanvienvanphong");

        // Thiết lập thời gian sống của cookie (nếu cần)
        cookie.setMaxAge(3600); // Ví dụ: cookie tồn tại trong 1 giờ

        // Thêm cookie vào phản hồi (response)
        response.addCookie(cookie);

        return ApiResponse.<ThongTinLoginResponse>builder()
                .result(nhanVienService.luuthongtinnhanvien(manhanvien,machucvu))
                .build();
    }
}
