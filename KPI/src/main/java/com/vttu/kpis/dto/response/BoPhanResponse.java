package com.vttu.kpis.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vttu.kpis.entity.DonVi;
import com.vttu.kpis.entity.NhanVien;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoPhanResponse {
    int mabophan;
    String tenbophan;

    DonVi donVi;
    @JsonIgnore
    Set<NhanVien> nhanViens;
}
