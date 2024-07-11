package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class ChucVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int machucvu;
    String tenchucvu;

    @OneToMany(mappedBy = "chucVu", fetch = FetchType.LAZY)
    @JsonBackReference
    Set<NhanVien_ChucVu > nhanVienChucVus;
}
