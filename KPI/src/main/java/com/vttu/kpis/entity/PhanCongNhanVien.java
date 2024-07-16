package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class PhanCongNhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long maphancongnhanvien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "ma_congviec", nullable = false)
    @NotNull(message = "Công việc không được phép trống")
    CongViec congViec;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinColumn(name = "ma_nhanvien", nullable = false)
    @NotNull(message = "Nhân viên không được phép trống")
    @JsonIgnoreProperties("phanCongLanhDaos")
    NhanVien nhanVien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("phanCongLanhDaos")
    @JoinColumn(name = "ma_quyen", nullable = false)
    @NotNull(message = "Quyền không được phép trống")
    Quyen quyen;
}
