package com.vttu.kpis.service;

import com.vttu.kpis.dto.response.QuyenResponse;
import com.vttu.kpis.mapper.QuyenMapper;
import com.vttu.kpis.responsitory.QuyenResponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class QuyenService {

    QuyenResponsitory quyenResponsitory;
    QuyenMapper quyenMapper;
    public List<QuyenResponse> getAll(){
        return quyenResponsitory.findAll().stream().map(quyenMapper::toQuyenResponse).toList();
    }
}
