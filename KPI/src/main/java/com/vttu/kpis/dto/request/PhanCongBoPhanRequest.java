package com.vttu.kpis.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vttu.kpis.entity.BoPhan;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.Quyen;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhanCongBoPhanRequest {

    Long maphancongbophan;

    @NotNull(message = "Công việc không được phép trống")
    CongViec congViec;

    @NotNull(message = "Bộ phận không được phép trống")
    BoPhan boPhan;

    @NotNull(message = "Quyền không được phép trống")
    Quyen quyen;
    boolean thuchienchinh;
}
