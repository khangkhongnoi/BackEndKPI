package com.vttu.kpis.dto.response;

import com.vttu.kpis.entity.NhomMucTieu;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MucTieuResponse {
    int mamuctieu;
    String tenmuctieu;
    LocalDate ngaybatdau;
    LocalDate ngayketthuc;
    String mota;
    Set<NhomMucTieu> nhomMucTieus;
}
