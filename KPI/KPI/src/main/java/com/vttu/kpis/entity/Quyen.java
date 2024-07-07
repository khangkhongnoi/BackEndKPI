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
public class Quyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int maquyen;
    String tenquyen;

    @OneToMany(mappedBy = "quyen",fetch = FetchType.LAZY)
            @JsonIgnore
    Set<PhanCongDonVi> phanCongDonVis;

    @OneToMany(mappedBy = "quyen", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<PhanCongLanhDao> phanCongLanhDaos;

    @OneToMany(mappedBy = "quyen", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<ChuyenTiepCongViec> chuyenTiepCongViecs;
}
