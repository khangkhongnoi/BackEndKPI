package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.ChuyenTiepCongViec;
import com.vttu.kpis.entity.CongViec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChuyenTiepCongViecResponsitory extends JpaRepository<ChuyenTiepCongViec, Long> {

    @Query(value = "SELECT * FROM chuyen_tiep_cong_viec as CT WHERE CT.ma_congviec = :macongviec", nativeQuery = true)
    List<ChuyenTiepCongViec> getChuyenTiepByMaCongViec(@Param("macongviec") String macongviec);



}
