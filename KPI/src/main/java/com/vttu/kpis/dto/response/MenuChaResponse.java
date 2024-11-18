package com.vttu.kpis.dto.response;

import com.vttu.kpis.entity.TaiKhoan;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuChaResponse {
    int mamenucha;
    String tenmenu;
    String mota;
    Set<TaiKhoan> taiKhoans = new HashSet<>();
}
