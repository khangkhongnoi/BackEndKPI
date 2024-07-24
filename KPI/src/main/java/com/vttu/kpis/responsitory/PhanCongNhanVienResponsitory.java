package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.PhanCongLanhDao;
import com.vttu.kpis.entity.PhanCongNhanVien;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface PhanCongNhanVienResponsitory extends JpaRepository<PhanCongNhanVien, Long> {

    @Query(value = "select count(*) from cong_viec\n" +
            "where cong_viec.ma_nguoitao = :manhanvien and cong_viec.macongvieccha = :macongviec",nativeQuery = true)
    long countByCheckTruocKhiXoa(@Param("macongviec") String macongviec, @Param("manhanvien") int manhanvien);

    @Query(value = "select count(*) from cong_viec where cong_viec.macongvieccha =:macongviec",nativeQuery = true)
    long countByTruongBPCheckTruocKhiXoa(@Param("macongviec") String macongviec);


    @Query(value = "select cong_viec.ma_trangthai from cong_viec\n" +
            "\tjoin phan_cong_nhan_vien on phan_cong_nhan_vien.ma_congviec = cong_viec.macongviec\n" +
            "\twhere cong_viec.macongviec = :macongviec\n" +
            "\tand phan_cong_nhan_vien.ma_nhanvien = :manhanvien and phan_cong_nhan_vien.thuchienchinh = true", nativeQuery = true)
    Map<String, Object> findByTrangThaiCongViec(String macongviec,int manhanvien);

    @Query(value = "select cong_viec.ma_trangthai from cong_viec\n" +
            "\tjoin phan_cong_nhan_vien on phan_cong_nhan_vien.ma_congviec = cong_viec.macongviec\n" +
            "\twhere cong_viec.macongviec = :macongviec",nativeQuery = true)
    Map<String, Object> findByTrangThaiCongViecNhanVienXoa(String macongviec);

    @Transactional
    @Modifying
    @Query("DELETE FROM PhanCongNhanVien pcls WHERE pcls.congViec.macongviec = :macongviec AND pcls.nhanVien.manhanvien = :manhanvien")
    void deleteByMaCongViecAndMaNhanVien(String macongviec, int manhanvien);

    @Transactional
    @Modifying
    @Query(value = "delete from phan_cong_nhan_vien where phan_cong_nhan_vien.ma_congviec =:macongviec",nativeQuery = true)
    void deleteByMaCongViec(String macongviec);

    @Query("SELECT pcls FROM PhanCongNhanVien pcls WHERE pcls.congViec.macongviec = :macongviec AND pcls.nhanVien.manhanvien = :manhanvien")
    PhanCongNhanVien listPhanCongNhanVienByMaCongViecAnMaNhanVien(String macongviec, int manhanvien);
}
