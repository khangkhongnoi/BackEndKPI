package com.vttu.kpis.controller;

import com.vttu.kpis.dto.request.LoginRequest;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.ThongTinLoginResponse;
import com.vttu.kpis.entity.NhanVien;
import com.vttu.kpis.entity.NhanVien_ChucVu;
import com.vttu.kpis.entity.TaiKhoan;
import com.vttu.kpis.service.NhanVienService;
import com.vttu.kpis.service.NhanVien_ChucVuService;
import com.vttu.kpis.service.TaiKhoanService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/taikhoan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaiKhoanController {

    TaiKhoanService taiKhoanService;
    NhanVienService nhanVienService;
    NhanVien_ChucVuService nhanVienChucVuService;

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
