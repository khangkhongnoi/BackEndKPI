package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.GiaHan;
import com.vttu.kpis.entity.XacNhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface XacNhanRespository extends JpaRepository<XacNhan, Long> {

    @Query(value = "select * from xac_nhan where xac_nhan.ma_congviec =:macongviec", nativeQuery = true)
    List<XacNhan> findXacNhanByMaCongViec(@Param("macongviec") String macongviec);

    @Query(value = "select * from xac_nhan where xac_nhan.ma_congviec = :macongviec\n" +
                "order by xac_nhan.thoigian desc limit 1", nativeQuery = true)
    XacNhan findXacNhanByThoigiantaoMaxAndMaCongViec(@Param("macongviec") String macongviec);

}
