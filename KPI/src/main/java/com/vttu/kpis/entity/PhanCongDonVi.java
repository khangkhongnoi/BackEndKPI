package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class PhanCongDonVi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long maphancongdonvi;

    @ManyToOne
//    @JsonIgnoreProperties("phanCongDonVis")
    @JsonBackReference
    @JoinColumn(name = "ma_congviec", nullable = false)
    @NotNull(message = "Công việc không được phép trống")
    CongViec congViec;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("phanCongDonVis")
    @JoinColumn(name = "ma_donvi", nullable = false)
    @NotNull(message = "Don vi không được phép trống")
    DonVi donVi;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnoreProperties("phanCongDonVis")
    @JoinColumn(name = "ma_quyen", nullable = false)
    @NotNull(message = "Quyền không được phép trống")
    Quyen quyen;
}
