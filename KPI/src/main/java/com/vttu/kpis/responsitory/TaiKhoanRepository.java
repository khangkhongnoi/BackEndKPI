package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {

   Optional<TaiKhoan> findByTentaikhoan(String tentaikhoan);

   @Query("SELECT tk from  TaiKhoan tk where tk.tentaikhoan = :tentaikhoan")
    TaiKhoan kiemtradangnhap(String tentaikhoan);
}
