package com.vttu.kpis.mapper;


import com.vttu.kpis.dto.request.ChuyenTiepCongViecRequest;
import com.vttu.kpis.dto.request.CongViecChuyenTiepResquest;
import com.vttu.kpis.dto.request.CongViecConNhanVienRequest;
import com.vttu.kpis.dto.response.ChuyenTiepCongViecResponse;
import com.vttu.kpis.dto.response.CongViecConNhanVienResponse;
import com.vttu.kpis.entity.CongViec;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CongViecChuyenTiepMapper {
   // CongViec toCreateCongViecChuyenTiep(CongViecChuyenTiepResquest request);
  //  ChuyenTiepCongViecResponse toCongViecConNhanVienResponse(CongViec congViec);
}
