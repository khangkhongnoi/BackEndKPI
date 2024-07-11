package com.vttu.kpis.dto.response;

import com.vttu.kpis.dto.request.CongViecChuyenTiepResquest;
import com.vttu.kpis.dto.request.PhanCongLanhDaoRequest;
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
public class ChuyenTiepCongViecResponse {

    Set<CongViecChuyenTiepResquest> congViecChuyenTieps;
}
