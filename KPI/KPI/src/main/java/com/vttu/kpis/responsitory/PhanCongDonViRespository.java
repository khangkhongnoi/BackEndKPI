package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.PhanCongDonVi;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhanCongDonViRespository extends JpaRepository<PhanCongDonVi, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM PhanCongDonVi pcdv WHERE pcdv.congViec.macongviec = :maCongViec AND pcdv.donVi.madonvi = :maDonVi")
    void deleteByMaCongViecAndMaDonVi(String maCongViec, int maDonVi);

    @Query("SELECT pcdv FROM PhanCongDonVi pcdv WHERE pcdv.donVi.madonvi =:maDonVi ")
    List<PhanCongDonVi> getDonVi(int maDonVi);
}
