package com.vttu.kpis.dto.request;

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
public class MucTieuRequest {
    int mamuctieu;

    @NotBlank(message = "Tên Mục Tiêu không được phép trống")
    String tenmuctieu;

    @NotNull(message = "Ngày Bắt Đầu không được phép trống")
    @FutureOrPresent(message = "Ngày Bắt Đầu phải là ngày hiện tại hoặc ngày trong tương lai")
    LocalDate ngaybatdau;

    @NotNull(message = "Ngày Kết Thúc không được phép trống")
    @Future(message = "Ngày Kết Thúc phải là ngày trong tương lai")
    LocalDate ngayketthuc;

    @Size(max = 1000, message = "Mô Tả không được vượt quá 1000 ký tự")
    String mota;

    Set<NhomMucTieu> nhomMucTieus;
}
