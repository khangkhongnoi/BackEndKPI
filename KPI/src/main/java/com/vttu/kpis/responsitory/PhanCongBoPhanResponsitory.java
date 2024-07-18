package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.PhanCongBoPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhanCongBoPhanResponsitory extends JpaRepository<PhanCongBoPhan, Long> {

    @Query(value = "select count(*) from cong_viec \n" +
            "join nhan_vien on cong_viec.ma_nguoitao = nhan_vien.manhanvien\n" +
            "where nhan_vien.ma_donvi = :maDonVi and cong_viec.macongvieccha = :maCongViec",nativeQuery = true)
    long countByCheckTruocKhiXoa(@Param("maCongViec") String maCongViec, @Param("maDonVi") int maDonVi);



}
