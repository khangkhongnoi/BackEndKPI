package com.vttu.kpis.service;

import com.vttu.kpis.dto.response.FilesResponse;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.entity.Files;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.mapper.FilesMapper;
import com.vttu.kpis.responsitory.CongViecResponsitory;
import com.vttu.kpis.responsitory.FilesResponsitory;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilesService {

    @Autowired
    FilesResponsitory filesResponsitory;
    @Autowired
    CongViecResponsitory congViecResponsitory;
    @Autowired
    FilesMapper filesMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path root;

    @PostConstruct
    public void init() {
        root = Paths.get(uploadDir);
        java.io.File uploadDirectory = root.toFile();
        if (!uploadDirectory.exists()) {
            if (!uploadDirectory.mkdirs()) {
                throw new RuntimeException("Could not initialize folder for upload!");
            }
        }
    }

    public FilesResponse saveFiles(Files filesRequest){

        congViecResponsitory.findById(filesRequest.getCongViec().getMacongviec())
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

        filesResponsitory.save(filesRequest);

        return filesMapper.toFilesResponse(filesRequest);
    }

    public List<FilesResponse> getFiles(String macongviec){
        congViecResponsitory.findById(macongviec)
                .orElseThrow(() -> new AppException(ErrorCode.CongViec_NOT_EXISTED));

        return filesResponsitory.findByMaCongViec(macongviec).stream().map(filesMapper::toFilesResponse).toList();
    }

    public Resource loadFileAsResource(String filename) throws MalformedURLException {
        java.io.File file = new java.io.File(root.toFile(), filename);
        if (file.exists() && file.canRead()) {
            return new UrlResource(file.toURI());
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }
}
