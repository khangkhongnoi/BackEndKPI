package com.vttu.kpis.dto.request;

import com.vttu.kpis.entity.CongViec;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DanhChoResquest {

    @NotNull(message = "Dành cho không được phép trống")
    int madanhcho;
    String tendanhcho;
    Set<CongViec> congViecs;
}
