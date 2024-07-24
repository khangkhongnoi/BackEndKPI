package com.vttu.kpis.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoPhanRequest {
    int mabophan;
    @NotNull(message = "Tên bộ phận không được phép trống")
    String tenbophan;
    @NotNull(message = "Mô tả không được phép trống")
    String mota;
    DonViRequest donVi;
}
