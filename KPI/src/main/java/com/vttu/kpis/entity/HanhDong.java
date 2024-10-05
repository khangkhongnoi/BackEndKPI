package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class HanhDong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int mahanhdong;
    String hanhdong;
    String mota;

    @OneToMany(mappedBy = "hanhDong" , cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Log_GiaoViec> logGiaoViecs;
}
