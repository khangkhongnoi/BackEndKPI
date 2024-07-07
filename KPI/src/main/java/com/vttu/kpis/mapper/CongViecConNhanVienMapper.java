package com.vttu.kpis.mapper;


import com.vttu.kpis.dto.request.CongViecConNhanVienRequest;
import com.vttu.kpis.dto.request.CongViecRequest;
import com.vttu.kpis.dto.response.CongViecConNhanVienResponse;
import com.vttu.kpis.dto.response.CongViecResponse;
import com.vttu.kpis.entity.CongViec;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { PhanCongLanhDaoMapper.class})
public interface CongViecConNhanVienMapper {
    CongViec toCreateCongViec(CongViecConNhanVienRequest request);
//    @Mapping(target = "phanCongLanhDaos", source = "phanCongLanhDaos")
    CongViecConNhanVienResponse toCongViecConNhanVienResponse(CongViec congViec);
}
