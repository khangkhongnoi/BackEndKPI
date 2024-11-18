package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.TaiKhoanPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TaiKhoanPermissionResponsitory extends JpaRepository<TaiKhoanPermission, Integer> {

    @Query(value = "SELECT p.name as permission_parent , pc.mapermissioncon as permission_child FROM\n" +
            "taikhoan_permissions tp\n" +
            "join permission p on tp.permission_id = p.name\n" +
            "join permission_con pc on tp.permission_con_id = pc.mapermissioncon\n" +
            "where tp.taikhoan_id =:taikhoan_id", nativeQuery = true)
    List<Map<String,Object>> getThongTinTaiKhoanByID(@Param("taikhoan_id") int taikhoan_id);

}
