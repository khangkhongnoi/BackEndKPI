package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.response.PhanCongDonViResponse;
import com.vttu.kpis.entity.PhanCongDonVi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DonViMapper.class})
public interface PhanCongDonViMapper {


    @Mapping(target = "donVi", source = "donVi")
    PhanCongDonViResponse toPhanCongDonViResponse(PhanCongDonVi phanCongDonVi);

    PhanCongDonVi toPhanCongDonVi(PhanCongDonViResponse phanCongDonViResponse);
}
