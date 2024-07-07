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
public class NhomMucTieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int manhom;
    String tennhom;
    String mamau;

    @OneToMany(mappedBy = "nhomMucTieu")
    @JsonIgnore
    Set<CongViec> congViecs;

    @ManyToMany(mappedBy = "nhomMucTieus", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<MucTieu> mucTieus;
}
