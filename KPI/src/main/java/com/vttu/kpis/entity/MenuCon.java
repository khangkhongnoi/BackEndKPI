package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class MenuCon {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int mamenu;
    String tenmenu;
    String duongdan;
    String mota;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ma_menucha", nullable = false)
    MenuCha menuCha;
}
