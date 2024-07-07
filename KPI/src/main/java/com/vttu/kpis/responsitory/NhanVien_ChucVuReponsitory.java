package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.NhanVien_ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NhanVien_ChucVuReponsitory extends JpaRepository<NhanVien_ChucVu, Long> {


    @Query(value = "  SELECT * FROM nhan_vien_chuc_vu WHERE manhanvien = :manhanvien", nativeQuery = true)
    List<NhanVien_ChucVu> findNhanVien_ChucVuByNhanVien(@Param("manhanvien") int manhanvien);

}
