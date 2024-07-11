package com.vttu.kpis.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vttu.kpis.entity.PhanCongDonVi;
import com.vttu.kpis.entity.PhanCongLanhDao;
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
public class QuyenResponse {

    int maquyen;
    String tenquyen;

    Set<PhanCongDonVi> phanCongDonViSet;

    Set<PhanCongLanhDao> phanCongLanhDaos;
}
