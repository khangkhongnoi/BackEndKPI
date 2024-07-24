package com.vttu.kpis.responsitory;

import com.vttu.kpis.dto.DonViDTO;
import com.vttu.kpis.dto.response.DonViResponse;
import com.vttu.kpis.entity.DonVi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonViResponsitory extends JpaRepository<DonVi, Integer> {

// lấy danh sách hơn vị khác trừ đơn vị ban lãnh đạo // != 1 là mã đơn vị ban lãnh đạo
    @Query("SELECT DV FROM DonVi DV WHERE DV.madonvi != 1 AND DV.madonvi != :madonvi")
    List<DonVi> getDonViPhoiHopThucHien(int madonvi);

    boolean existsByTendonvi(String tendonvi);

    @Query(value = "select  don_vi.madonvi,don_vi.tendonvi,don_vi.mota from don_vi", nativeQuery = true)
    List<DonVi> getDonVi();

    @Query("SELECT new com.vttu.kpis.dto.DonViDTO(d.madonvi, d.tendonvi, d.mota) FROM DonVi d")

    List<DonViDTO> findDonViDTO();
    @Query("SELECT new com.vttu.kpis.dto.DonViDTO(d.madonvi, d.tendonvi, d.mota) FROM DonVi d WHERE d.madonvi =:madovi")
    DonViDTO findDonViDTOId(int madovi);

    boolean existsByTendonviAndMadonviNot(String tendonvi, int madonvi);
}
