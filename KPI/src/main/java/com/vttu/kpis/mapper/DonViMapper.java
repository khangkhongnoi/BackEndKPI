package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.response.DonViResponse;
import com.vttu.kpis.entity.DonVi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonViMapper {
    DonViResponse toDonViResponse(DonVi donVi);
    DonVi toDonVi(DonViResponse donViResponse);
}
