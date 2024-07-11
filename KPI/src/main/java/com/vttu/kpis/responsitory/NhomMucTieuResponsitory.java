package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.NhomMucTieu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NhomMucTieuResponsitory extends JpaRepository<NhomMucTieu, Integer> {

    boolean existsByTennhom(String tennhom);
}
