package com.vttu.kpis.dto.response;


import com.vttu.kpis.entity.BoPhan;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.Quyen;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhanCongBoPhanResponse {

    Long maphancongbophan;
    CongViec congViec;
    BoPhan boPhan;
    Quyen quyen;
}
