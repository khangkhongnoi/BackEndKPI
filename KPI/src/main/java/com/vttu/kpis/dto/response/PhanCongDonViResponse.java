package com.vttu.kpis.dto.response;

import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.DonVi;
import com.vttu.kpis.entity.Quyen;
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
public class PhanCongDonViResponse {
    Long maphancongdonvi;
    CongViecResponse congViec;
    DonViResponse donVi;
    QuyenResponse quyen;
    boolean thuchienchinh;
}
