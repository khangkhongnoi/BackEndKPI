package com.vttu.kpis.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Permission_Con {
    @Id
    String mapermissioncon;
    String tenpermissioncon;
    String duongdan;
    String mota;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ma_permission", nullable = false)
    Permission permission;

}
