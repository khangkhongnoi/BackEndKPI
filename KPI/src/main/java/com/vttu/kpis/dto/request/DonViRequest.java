package com.vttu.kpis.dto.request;

import com.vttu.kpis.entity.BoPhan;
import com.vttu.kpis.entity.PhanCongDonVi;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
    String tendonvi;
    Set<BoPhan> boPhans;
    Set<PhanCongDonVi> phanCongDonVis;
}
