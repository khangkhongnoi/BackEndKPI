package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.response.ChucVuResponse;
import com.vttu.kpis.entity.ChucVu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChucVuMapper {

    ChucVuResponse toChucVuResponse(ChucVu chucVu);
}
