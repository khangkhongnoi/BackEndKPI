package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {

   Optional<TaiKhoan> findByTentaikhoan(String tentaikhoan);

   @Query("SELECT tk from  TaiKhoan tk where tk.tentaikhoan = :tentaikhoan")
    TaiKhoan kiemtradangnhap(String tentaikhoan);

   @Query(value = "select tai_khoan.mataikhoan, tai_khoan.tentaikhoan,nhan_vien.tennhanvien\n" +
           "from tai_khoan \n" +
           "join nhan_vien on nhan_vien.manhanvien = tai_khoan.ma_nhanvien", nativeQuery = true)
    List<Map<String,Object>> listtaikhoan();
}
