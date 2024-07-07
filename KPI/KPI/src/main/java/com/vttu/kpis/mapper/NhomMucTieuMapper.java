package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.request.NhomMucTieuRequest;
import com.vttu.kpis.dto.response.NhomMucTieuResponse;
import com.vttu.kpis.entity.NhomMucTieu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NhomMucTieuMapper {

    NhomMucTieuResponse toNhomMucTieuResponse(NhomMucTieu nhomMucTieu);

    void updateNhom(@MappingTarget NhomMucTieu nhomMucTieu, NhomMucTieuRequest request);
}
