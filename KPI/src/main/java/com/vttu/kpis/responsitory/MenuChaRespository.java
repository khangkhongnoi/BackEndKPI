package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.MenuCha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface MenuChaRespository extends JpaRepository<MenuCha, Integer> {

    @Query(value = "select mamenucha, menu_cha.tenmenu, menu_con.tenmenu as tenmenucon, duongdan from taikhoan_menu\n" +
            "left join menu_cha on menu_cha.mamenucha = taikhoan_menu.menu_id\n" +
            "left join menu_con on menu_con.ma_menucha = menu_cha.mamenucha where taikhoan_id  = :mataikhoan ", nativeQuery = true)
    List<Map<String,Object>> getMenuByMataikhoan(Integer mataikhoan);
}
