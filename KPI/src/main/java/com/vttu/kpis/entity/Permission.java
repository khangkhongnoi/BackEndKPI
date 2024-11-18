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
public class Permission {

    @Id
    String name;
    String description;

    @ManyToMany
    @JoinTable(
            name = "permission_role",
            joinColumns = @JoinColumn(name = "permission_name"),
            inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "permission")
    @JsonIgnore
    Set<Permission_Con> permissionCons;

    @OneToMany(mappedBy = "permission")
    @JsonIgnore
    Set<TaiKhoanPermission> tkPermissonS;

}
