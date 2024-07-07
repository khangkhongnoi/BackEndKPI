package com.vttu.kpis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Role {

    @Id
    String name;
    String description;

    @ManyToMany(mappedBy = "roles")
    Set<TaiKhoan> taiKhoans = new HashSet<>();
    @ManyToMany(mappedBy = "roles")
    Set<Permission> permissions = new HashSet<>();
}
