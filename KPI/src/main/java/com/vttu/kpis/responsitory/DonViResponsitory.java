package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.DonVi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonViResponsitory extends JpaRepository<DonVi, Integer> {

// lấy danh sách hơn vị khác trừ đơn vị ban lãnh đạo // != 1 là mã đơn vị ban lãnh đạo
    @Query("SELECT DV FROM DonVi DV WHERE DV.madonvi != 1 AND DV.madonvi != :madonvi")
    List<DonVi> getDonViPhoiHopThucHien(int madonvi);
}
