package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "taikhoan_permissions")
public class TaiKhoanPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "taikhoan_id")
    TaiKhoan taiKhoan;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "permission_id")
    Permission permission;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "permission_con_id")
    Permission_Con permissionCon;
}
