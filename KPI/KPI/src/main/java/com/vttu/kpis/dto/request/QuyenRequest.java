package com.vttu.kpis.dto.request;

import com.vttu.kpis.entity.ChuyenTiepCongViec;
import com.vttu.kpis.entity.PhanCongDonVi;
import com.vttu.kpis.entity.PhanCongLanhDao;
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
public class QuyenRequest {

    int maquyen;
    String tenquyen;
    Set<PhanCongDonVi> phanCongDonViSet;
    Set<PhanCongLanhDao> phanCongLanhDaos;
    Set<ChuyenTiepCongViec> chuyenTiepCongViecs;

}
