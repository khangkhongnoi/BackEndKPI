package com.vttu.kpis.responsitory;

import com.vttu.kpis.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenResponsitory extends JpaRepository<InvalidatedToken, String> {
}
