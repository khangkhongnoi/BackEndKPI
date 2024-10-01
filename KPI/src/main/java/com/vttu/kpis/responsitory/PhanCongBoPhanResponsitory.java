package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.PhanCongBoPhan;
import com.vttu.kpis.entity.PhanCongDonVi;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PhanCongBoPhanResponsitory extends JpaRepository<PhanCongBoPhan, Long> {

    @Query(value = "select count(*) from cong_viec \n" +
            "       join nhan_vien on cong_viec.ma_nguoitao = nhan_vien.manhanvien\n" +
            "join nhan_vien_chuc_vu on nhan_vien_chuc_vu.manhanvien = nhan_vien.manhanvien\n" +
            "where nhan_vien_chuc_vu.mabophan = :maBoPhan and cong_viec.macongvieccha =  :maCongViec",nativeQuery = true)
    long countByCheckTruocKhiXoa(@Param("maCongViec") String maCongViec,@Param("maBoPhan") int maBoPhan);

    @Query(value = "select cong_viec.ma_trangthai from cong_viec\n" +
            "join phan_cong_bo_phan on phan_cong_bo_phan.ma_congviec = cong_viec.macongviec\n" +
            "where cong_viec.macongviec = :macongviec \n" +
            "and phan_cong_bo_phan.ma_bophan = :mabophan", nativeQuery = true)
    Map<String, Object> findByTrangThaiCongViec(String macongviec, int mabophan);

    @Query("SELECT pcbp FROM PhanCongBoPhan pcbp where pcbp.congViec.macongviec = :maCongViec AND pcbp.boPhan.mabophan =:maBoPhan")
    PhanCongBoPhan listPhanCongCongViecByMaCongViecAnMaBoPhan(String maCongViec, int maBoPhan);

    @Transactional
    @Modifying
    @Query("DELETE FROM PhanCongBoPhan pcbp WHERE pcbp.congViec.macongviec = :maCongViec AND pcbp.boPhan.mabophan = :maBoPhan")
    void deleteByMaCongViecAndMaBoPhan(String maCongViec, int maBoPhan);
}
