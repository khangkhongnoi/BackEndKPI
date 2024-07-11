package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.response.BoPhanResponse;
import com.vttu.kpis.entity.BoPhan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoPhanMapper {

    BoPhanResponse toBophanResponse(BoPhan boPhan);
}
