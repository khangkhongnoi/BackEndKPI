package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilesResponsitory extends JpaRepository<Files, Long> {

    @Query(value = "select * from files where ma_congviec = :macongviec" , nativeQuery = true)
    List<Files> findByMaCongViec(String macongviec);
}
