package com.vttu.kpis.dto.request;

import com.vttu.kpis.entity.BoPhan;
import com.vttu.kpis.entity.PhanCongDonVi;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonViRequest {

    int madonvi;
    @NotNull(message = "Tên đơn vị không được phép trống")
    String tendonvi;
    @NotNull(message = "Mô tả không được phép trống")
    String mota;
    Set<BoPhan> boPhans;
    Set<PhanCongDonVi> phanCongDonVis;
}
