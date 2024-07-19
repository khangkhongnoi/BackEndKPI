package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.CongViec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public interface CongViecResponsitory extends JpaRepository<CongViec, String> {

    boolean existsByTencongviec(String tencongviec);

    //check xem đơn vị có tạo công việc con hay chưa
//    @Query(value = "select COUNT(*) from cong_viec\n" +
//            "join phan_cong_don_vi on phan_cong_don_vi.ma_congviec = cong_viec.macongviec\n" +
//            " where\n" +
//            "macongvieccha = :macongviec and ma_donvi = :madonvi", nativeQuery = true)
//    int findbycongviec(@Param("macongviec") String macongviec, @Param("madonvi") int madonvi);


    //check xem những người trong ban lãnh đạo tạo công việc con hay chưa
//    @Query(value = "select COUNT(*) from cong_viec\n" +
//            "join phan_cong_lanh_dao on phan_cong_lanh_dao.ma_congviec = cong_viec.macongviec\n" +
//            " where\n" +
//            "macongvieccha = :macongviec and ma_nhanvien = :manhanvien", nativeQuery = true)
//    int SelectCountBanLanhDao(@Param("macongviec") String macongviec, @Param("manhanvien") int manhanvien);


    @Query(value = "SELECT * FROM cong_viec\n" +
            "join phan_cong_don_vi ON phan_cong_don_vi.ma_congviec = cong_viec.macongviec\n" +
            "where phan_cong_don_vi.ma_donvi =:madonvi and cong_viec.macongvieccha = '0'", nativeQuery = true)
    List<CongViec> getCongViecDonVi(@Param("madonvi") int madonvi);

    @Query(value = "SELECT * FROM  cong_viec CV\n" +
            "            join phan_cong_bo_phan on phan_cong_bo_phan.ma_congviec = CV.macongviec\n" +
            "            WHERE phan_cong_bo_phan.ma_bophan = :mabophan", nativeQuery = true)
    List<CongViec> getCongViecBoPhanNhan(@Param("mabophan") int mabophan);

    @Query(value = "SELECT * FROM  cong_viec CV\n" +
            "            join phan_cong_nhan_vien on phan_cong_nhan_vien.ma_congviec = CV.macongviec\n" +
            "            WHERE phan_cong_nhan_vien.ma_nhanvien =:manhanvien and cv.ma_nguoitao !=:manhanvien", nativeQuery = true)
    List<CongViec> getCongViecNhanVienNhan(@Param("manhanvien") int manhanvien);
    @Query(value = "SELECT * FROM cong_viec\n" +
            "           join phan_cong_lanh_dao ON phan_cong_lanh_dao.ma_congviec = cong_viec.macongviec\n" +
            "            where phan_cong_lanh_dao.ma_nhanvien = :manhanvein and cong_viec.macongvieccha = '0'\n", nativeQuery = true)
    List<CongViec> getCongViecBanLanhDao(@Param("manhanvein") int manhanvein);

 @Query("SELECT CV FROM  CongViec CV WHERE CV.ma_nguoitao =:ma_nguoitao AND CV.macongvieccha = '0'")
    List<CongViec> findByMa_nguoitao(int ma_nguoitao);

   Optional<CongViec> findByMacongviec(String macongviec);

    @Query(value = "SELECT * FROM cong_viec\n" +
            "join phan_cong_don_vi ON phan_cong_don_vi.ma_congviec = cong_viec.macongviec\n" +
            "where phan_cong_don_vi.ma_donvi =:madonvi and cong_viec.macongvieccha = '0' and cong_viec.ma_nguoitao =:manhanvien", nativeQuery = true)
    List<CongViec> getCongViecDonViByNguoiTao(@Param("madonvi") int madonvi, @Param("manhanvien") int manhanvien);

    @Query(value = "select * from cong_viec \n" +
            "left join chuyen_tiep_cong_viec ON chuyen_tiep_cong_viec.ma_congviec = cong_viec.macongviec\n" +
            "where  chuyen_tiep_cong_viec.ma_nhanvien = :manhanvien", nativeQuery = true)
    List<CongViec> getCongViecChuyenTiepByMaNhanVien(@Param("manhanvien") int manhanvien);

    @Query("SELECT CV FROM CongViec CV WHERE CV.macongvieccha=:macongvieccha")
        List<CongViec> getCongViecConTheoMaCongViecCha(String macongvieccha);

    @Query(value = "select * from cong_viec where cong_viec.macongvieccha =:macongviec", nativeQuery = true)
    List<Map<String,Object>> getCongViecTheoMaCongViecGoc(String macongviec);

    @Query(value = "select * from cong_viec" +
            " where cong_viec.macongviec =:macongvieccha", nativeQuery = true)
    List<CongViec> getCongViecConTheoMaCongViecChaGoc(String macongvieccha);

    @Query("SELECT CV FROM CongViec CV WHERE CV.macongvieccha=:macongvieccha AND CV.macongviec =:macongviec")
    CongViec getCongViecConTheoMaCongViecChaVaCon(String macongvieccha,String macongviec);
    @Query(value = "SELECT * FROM  cong_viec CV\n" +
            "join phan_cong_don_vi on phan_cong_don_vi.ma_congviec = CV.macongviec\n" +
            "WHERE phan_cong_don_vi.ma_donvi = :madonvi and CV.macongvieccha = '0'",nativeQuery = true)
    List<CongViec> congviecphancongchodonvi (@Param("madonvi") int madonvi);

    @Query(value = "SELECT * FROM  cong_viec CV\n" +
            "join phan_cong_don_vi on phan_cong_don_vi.ma_congviec = CV.macongviec\n" +
            "WHERE phan_cong_don_vi.ma_donvi = :madonvi ",nativeQuery = true)
    List<CongViec> getCongViecDonViByHieuTruongChucVuTruongDonVi (@Param("madonvi") int madonvi);

    @Modifying
    @Transactional
    @Query("UPDATE CongViec CV SET CV.trangThaiCongViec.matrangthai =:matrangthai where CV.macongviec =:macongviec")
    void updateCongViecByTrangThaiCongViec(int matrangthai,String macongviec);

    @Modifying
    @Transactional
    @Query(value = "update cong_viec set phantramhoanthanh = :phantramhoanthanh  where cong_viec.macongviec=:macongviec ", nativeQuery = true)
    void updatePhanTramHoanThanhCongViec(@Param("phantramhoanthanh") float phantramhoanthanh,@Param("macongviec") String macongviec);

    @Modifying
    @Transactional
    @Query(value = "update cong_viec set ma_ketqua =:ma_ketqua where cong_viec.macongviec =:macongviec", nativeQuery = true)
    void updateTinhKetQuaCongViec(@Param("ma_ketqua") int ma_ketqua,@Param("macongviec") String macongviec);
}
