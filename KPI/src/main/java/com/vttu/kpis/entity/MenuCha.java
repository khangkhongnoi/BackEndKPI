package com.vttu.kpis.entity;

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
public class MenuCha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int mamenucha;
    String tenmenu;
    String mota;


}
