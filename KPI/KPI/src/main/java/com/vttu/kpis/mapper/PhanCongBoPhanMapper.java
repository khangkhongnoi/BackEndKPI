package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.response.PhanCongBoPhanResponse;
import com.vttu.kpis.dto.response.PhanCongLanhDaoResponse;
import com.vttu.kpis.entity.PhanCongBoPhan;
import com.vttu.kpis.entity.PhanCongLanhDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhanCongBoPhanMapper {
    PhanCongBoPhan toPhanCongBoPhan(PhanCongBoPhanResponse phanCongBoPhanResponse);

    PhanCongBoPhanResponse toPhanCongBoPhanResponse(PhanCongBoPhan phanCongBoPhan);
}
