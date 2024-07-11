package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int mataikhoan;
    String tentaikhoan;
    String matkhau;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ma_nhanvien")
    NhanVien nhanVien;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "taikhoan_role",
            joinColumns = @JoinColumn(name = "taikhoan_id"),
            inverseJoinColumns  = @JoinColumn(name = "role_id")
    )
    Set<Role> roles = new HashSet<>();

}
