package com.vttu.kpis.responsitory;

import com.vttu.kpis.dto.response.ThongTinLoginResponse;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NhanVienResponsitory extends JpaRepository<NhanVien, Integer> {

    @Query(value = "SELECT nhan_vien.manhanvien AS manhanvien, nhan_vien_chuc_vu.ma_chuc_vu AS machucvu, nhan_vien.ma_donvi AS madonvi, nhan_vien.tennhanvien AS tennhnavien FROM nhan_vien_chuc_vu " +
            "JOIN nhan_vien ON nhan_vien.manhanvien = nhan_vien_chuc_vu.ma_nhan_vien " +
            "WHERE nhan_vien_chuc_vu.ma_nhan_vien = :manhanvien AND nhan_vien_chuc_vu.ma_chuc_vu = :machucvu", nativeQuery = true)
    ThongTinLoginResponse findByManhanvienAndChucVuss(@Param("manhanvien") int manhanvien,@Param("machucvu") int machucvu);


    @Query("SELECT nv FROM NhanVien  nv LEFT JOIN  nv.nhanVienChucVus nvcv where nvcv.chucVu.machucvu =:machucvu")
    List<NhanVien> getAllBanLanhDao(int machucvu);

    @Query("SELECT nv FROM NhanVien nv where nv.donVi.madonvi =:madonvi")
    List<NhanVien> getNhanVienDonVi(int madonvi);

    @Query("SELECT nv FROM NhanVien nv where nv.boPhan.mabophan =:mabophan")
    List<NhanVien> getNhanVienBoPhan(int mabophan);

    @Query("SELECT NV FROM NhanVien NV LEFT JOIN NV.chuyenTiepCongViecs ct WHERE ct.congViec.macongviec = :macongviec")
        List<NhanVien> getNhanVienDuocNhanChuyenTiepCongViecByMaCongViec (String macongviec);



}
