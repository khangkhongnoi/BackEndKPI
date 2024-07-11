package com.vttu.kpis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PhanCongBoPhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long maphancongbophan;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ma_congviec", nullable = false)
    @NotNull(message = "Công việc không được phép trống")
    CongViec congViec;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_bophan", nullable = false)
    @NotNull(message = "Bộ phận không được phép trống")
    BoPhan boPhan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "ma_quyen", nullable = false)
    @NotNull(message = "Quyền không được phép trống")
    Quyen quyen;

}
