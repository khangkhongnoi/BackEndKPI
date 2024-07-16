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
public class TrangThaiCongViec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int matrangthai;
    String tentrangthai;

    @OneToMany(mappedBy = "trangThaiCongViec")
    @JsonIgnore
    Set<CongViec> congViecs;
}
