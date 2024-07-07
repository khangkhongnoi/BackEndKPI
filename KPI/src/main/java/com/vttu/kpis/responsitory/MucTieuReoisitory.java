package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.MucTieu;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MucTieuReoisitory extends JpaRepository<MucTieu , Integer> {
    boolean existsByTenmuctieu(String tenmuctieu);
    boolean existsByTenmuctieuAndMamuctieuNot(String tenmuctieu, int mamuctieu);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM muc_tieu_nhom_muc_tieu WHERE mamuctieu = :mamuctieu", nativeQuery = true)
    void deleteAllNhomMucTieuByMucTieuId(@Param("mamuctieu") int mucTieuId);
}
