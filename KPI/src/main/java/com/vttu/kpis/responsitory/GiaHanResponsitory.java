package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.GiaHan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GiaHanResponsitory extends JpaRepository<GiaHan, Integer> {

    @Query(value = "select * from gia_han where gia_han.ma_congviec = :macongviec\n" +
            "order by gia_han.thoigiantao desc limit 1", nativeQuery = true)
    GiaHan findGiaHanByThoigiantaoMaxAndMaCongViec(@Param("macongviec") String macongviec);

    @Query(value = "select * from gia_han where gia_han.ma_congviec =:maCongViec", nativeQuery = true)
    List<GiaHan> findGiaHanByMaCongViec(String maCongViec);
}
