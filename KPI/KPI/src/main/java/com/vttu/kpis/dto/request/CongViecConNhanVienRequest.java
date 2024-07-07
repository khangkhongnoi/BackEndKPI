package com.vttu.kpis.dto.request;

import com.vttu.kpis.entity.DanhCho;
import com.vttu.kpis.entity.NhomMucTieu;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CongViecConNhanVienRequest {

    String macongviec;
    @NotBlank(message = "Tên Công Việc không được phép trống")
    String tencongviec;

    @NotNull(message = "Ngày Bắt Đầu không được phép trống")
    @FutureOrPresent(message = "Ngày Bắt Đầu phải là ngày hiện tại hoặc ngày trong tương lai")
    LocalDate ngaybatdau;

    @NotNull(message = "Ngày Kết Thúc không được phép trống")
    @FutureOrPresent(message = "Ngày Kết Thúc phải là ngày hiện tại hoặc ngày trong tương lai")
    LocalDate ngayketthucdukien;

    @NotNull(message = "Trọng Số không được phép trống")
    @Max(value = 100, message = "Trọng Số không được vượt quá 100")
    @Min(value = 0, message = "Trọng Số không được nhỏ hơn 0")
    Integer trongso;
    int trangthaicongviec;
    float phantramhoanthanh;

    @NotNull(message = "Công việc cha không được trống")
    String macongvieccha;

    @NotNull(message = "Người tạo không được phép trống")
    Integer  ma_nguoitao;
    String ten_nguoitao;

    @NotNull(message = "Dành cho không được phép trống")
    DanhChoResquest danhCho;
    Set<PhanCongLanhDaoRequest> phanCongLanhDaos;
    Set<PhanCongDonViRequest> phanCongDonVis;
}
