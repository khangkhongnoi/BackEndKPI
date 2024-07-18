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
public class KetQuaCongViec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int maketqua;
    String tenketqua;

    @OneToMany(mappedBy = "ketQuaCongViec")
    @JsonIgnore
    Set<CongViec> congViecs;
}
