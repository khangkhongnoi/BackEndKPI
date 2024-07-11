package com.vttu.kpis.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class MucTieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int mamuctieu;
    String tenmuctieu;
    LocalDate ngaybatdau;
    LocalDate  ngayketthuc;
    String mota;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "MucTieu_NhomMucTieu",
            joinColumns = @JoinColumn(name = "mamuctieu"),
            inverseJoinColumns = @JoinColumn(name = "manhom")
    )
    Set<NhomMucTieu> nhomMucTieus;
}
