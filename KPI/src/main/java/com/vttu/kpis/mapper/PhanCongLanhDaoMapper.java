package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.response.PhanCongDonViResponse;
import com.vttu.kpis.dto.response.PhanCongLanhDaoResponse;
import com.vttu.kpis.entity.PhanCongDonVi;
import com.vttu.kpis.entity.PhanCongLanhDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhanCongLanhDaoMapper {

    PhanCongLanhDao toPhanCongLanhDao(PhanCongLanhDaoResponse phanCongLanhDaoResponse);

    PhanCongLanhDaoResponse toPhanCongLanhDaoResponse(PhanCongLanhDao phanCongLanhDao);
}
