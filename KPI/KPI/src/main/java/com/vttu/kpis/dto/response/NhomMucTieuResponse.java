package com.vttu.kpis.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhomMucTieuResponse {

    int manhom;
    String tennhom;
    String mamau;
}
