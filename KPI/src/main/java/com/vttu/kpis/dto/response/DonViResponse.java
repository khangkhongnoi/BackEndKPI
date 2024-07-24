package com.vttu.kpis.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vttu.kpis.entity.BoPhan;
import com.vttu.kpis.entity.PhanCongDonVi;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonViResponse {
    int madonvi;
    String tendonvi;
    String mota;
    @JsonIgnore
    Set<BoPhan> boPhans;
    @JsonIgnore
    Set<PhanCongDonVi> phanCongDonVis;
}
