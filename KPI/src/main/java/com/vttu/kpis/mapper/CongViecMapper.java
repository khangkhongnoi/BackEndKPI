package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.request.CongViecRequest;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.PhanCongDonVi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {PhanCongDonViMapper.class, PhanCongLanhDaoMapper.class, ChuyenTiepCongViecMapper.class})
public interface CongViecMapper {

    CongViec toCreateCongViec(CongViecRequest request);

    @Mapping(target = "phanCongDonVis", source = "phanCongDonVis")
    @Mapping(target = "phanCongLanhDaos", source = "phanCongLanhDaos")
    @Mapping(target = "chuyenTiepCongViecs", source = "chuyenTiepCongViecs")
    CongViecResponse toCongViecResponse(CongViec congViec);
}
