package com.vttu.kpis.dto.response;

import com.vttu.kpis.dto.request.ChuyenTiepCongViecRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CongViecChuyenTiepResponse {

    Set<ChuyenTiepCongViecRequest> chuyenTiepCongViecs;
}
