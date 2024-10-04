package com.vttu.kpis.mapper;

import com.vttu.kpis.dto.response.FilesResponse;
import com.vttu.kpis.entity.Files;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FilesMapper {
    FilesResponse toFilesResponse(Files files);

}
