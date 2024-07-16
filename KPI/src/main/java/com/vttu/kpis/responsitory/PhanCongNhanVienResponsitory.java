package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.PhanCongLanhDao;
import com.vttu.kpis.entity.PhanCongNhanVien;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhanCongNhanVienResponsitory extends JpaRepository<PhanCongNhanVien, Integer> {

    @Query(value = "select count(*) from cong_viec\n" +
            "where cong_viec.ma_nguoitao = :manhanvien and cong_viec.macongvieccha = :macongviec",nativeQuery = true)
    long countByCheckTruocKhiXoa(@Param("macongviec") String macongviec, @Param("manhanvien") int manhanvien);

    @Transactional
    @Modifying
    @Query("DELETE FROM PhanCongNhanVien pcls WHERE pcls.congViec.macongviec = :macongviec AND pcls.nhanVien.manhanvien = :manhanvien")
    void deleteByMaCongViecAndMaNhanVien(String macongviec, int manhanvien);

    @Query("SELECT pcls FROM PhanCongNhanVien pcls WHERE pcls.congViec.macongviec = :macongviec AND pcls.nhanVien.manhanvien = :manhanvien")
    PhanCongNhanVien listPhanCongNhanVienByMaCongViecAnMaNhanVien(String macongviec, int manhanvien);
}
