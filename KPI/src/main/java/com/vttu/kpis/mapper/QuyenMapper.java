package com.vttu.kpis.mapper;


import com.vttu.kpis.dto.response.QuyenResponse;

import com.vttu.kpis.entity.Quyen;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuyenMapper {
    QuyenResponse toQuyenResponse(Quyen quyen);
    Quyen toQuyen (QuyenResponse quyenResponse);

}
