package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class NhanVien_ChucVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Ma_NhanVienChucVu;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "manhanvien")
    NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "machucvu")
    ChucVu chucVu;

    @ManyToOne
    @JoinColumn(name = "madonvi",nullable = true)
    DonVi donVi;

    @ManyToOne
    @JoinColumn(name = "mabophan", nullable = true)
    BoPhan boPhan;

}
