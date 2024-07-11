package com.vttu.kpis.mapper;


import com.vttu.kpis.dto.request.ChuyenTiepCongViecRequest;
import com.vttu.kpis.dto.request.PhanCongLanhDaoRequest;
import com.vttu.kpis.dto.response.ChuyenTiepCongViecResponse;
import com.vttu.kpis.entity.ChuyenTiepCongViec;
import com.vttu.kpis.entity.PhanCongLanhDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChuyenTiepCongViecMapper {

    ChuyenTiepCongViec toPhanCongChuyenTiep(ChuyenTiepCongViecRequest request);
    ChuyenTiepCongViecResponse toChuyenTiepCongViecResponse(ChuyenTiepCongViec chuyenTiepCongViec);
}
