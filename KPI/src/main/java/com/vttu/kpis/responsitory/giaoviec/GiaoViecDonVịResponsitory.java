package com.vttu.kpis.responsitory.giaoviec;

import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.PhanCongDonVi;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface GiaoViecDonVịResponsitory extends JpaRepository<CongViec, String> {

    @Query(value = "select cong_viec.ma_trangthai from cong_viec\n" +
            "join phan_cong_don_vi on phan_cong_don_vi.ma_congviec = cong_viec.macongviec\n" +
            "where cong_viec.macongviec = :macongviec \n" +
            "and phan_cong_don_vi.ma_donvi = :madonvi and phan_cong_don_vi.thuchienchinh = true", nativeQuery = true)
    Map<String, Object> findByTrangThaiCongViecDonVi(String macongviec, int madonvi);

    @Query(value = "select count(*) from cong_viec \n" +
            "join nhan_vien on cong_viec.ma_nguoitao = nhan_vien.manhanvien\n" +
            "join nhan_vien_chuc_vu on nhan_vien_chuc_vu.manhanvien = nhan_vien.manhanvien\n" +
            "where nhan_vien_chuc_vu.madonvi = :maDonVi and cong_viec.macongvieccha =  :maCongViec",nativeQuery = true)
    long countByCheckTruocKhiXoaDonVi(@Param("maCongViec") String maCongViec, @Param("maDonVi") int maDonVi);

    @Transactional
    @Modifying
    @Query("DELETE FROM PhanCongDonVi pcdv WHERE pcdv.congViec.macongviec = :maCongViec AND pcdv.donVi.madonvi = :maDonVi")
    void deleteDonViByMaCongViecAndMaDonVi(String maCongViec, int maDonVi);

    @Query(value = "select cong_viec.ma_trangthai from cong_viec\n" +
                "where cong_viec.macongviec = :macongviec \n", nativeQuery = true)
    Map<String, Object> findByTrangThaiDonViCongViecXoa(String macongviec);


}
