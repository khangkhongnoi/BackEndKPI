package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.PhanCongLanhDao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PhanCongLanhDaoRepository extends JpaRepository<PhanCongLanhDao, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM PhanCongLanhDao pcls WHERE pcls.congViec.macongviec = :macongviec AND pcls.nhanVien.manhanvien = :manhanvien")
    void deleteByMaCongViecAndMaNhanVien(String macongviec, int manhanvien);
}
