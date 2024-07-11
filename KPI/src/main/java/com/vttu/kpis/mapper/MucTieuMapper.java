package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.request.MucTieuRequest;
import com.vttu.kpis.dto.response.MucTieuResponse;
import com.vttu.kpis.entity.MucTieu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MucTieuMapper {

    MucTieu toMucTieu(MucTieuRequest request);
    MucTieuResponse toMucTieuResponse(MucTieu mucTieu);
}
