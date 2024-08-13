package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.BoPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface BoPhanResponsitory extends JpaRepository<BoPhan, Integer> {

    @Query("SELECT BP FROM BoPhan BP WHERE BP.donVi.madonvi =:madonvi ")
    List<BoPhan> findBoPhanByMadonvi (int madonvi);

    boolean existsByTenbophan(String tenbophan);
    boolean existsByTenbophanAndMabophanNot(String tenbophan, int mabophan);

    @Query(value = "select * from bo_phan where bo_phan.mabophan =:mabophan", nativeQuery = true)
    Map<String,Object> findBoPhanByMabophan(int mabophan);
}
