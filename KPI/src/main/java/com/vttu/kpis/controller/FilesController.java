package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.response.FilesResponse;
import com.vttu.kpis.entity.CongViec;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.service.FilesService;
import com.vttu.kpis.utils.CheckToken;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/upload-file")

public class FilesController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private FilesService filesService;

    @Autowired
    HttpServletRequest request;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("{macongviec}")
    public ResponseEntity<?> uploadFiles(@PathVariable String macongviec, @RequestParam("files") MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>();


        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                try {
                    for (MultipartFile file : files) {
                        // Tạo tên file duy nhất để tránh trùng lặp
                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        Path filePath = Paths.get(uploadDir, fileName);

                        // Lưu file vào thư mục
                        Files.copy(file.getInputStream(), filePath);

                        // Lưu đường dẫn file vào database
                        com.vttu.kpis.entity.Files fileEntity = new com.vttu.kpis.entity.Files();
                        fileEntity.setFile_name(fileName);
                        fileEntity.setFile_path(filePath.toString());
                        CongViec congViec = new CongViec();
                        congViec.setMacongviec(macongviec);
                        fileEntity.setCongViec(congViec);
                        filesService.saveFiles(fileEntity);

                    }

                    return ResponseEntity.ok("Files uploaded successfully: " + String.join(", ", fileNames));
                } catch (IOException e) {
                    return ResponseEntity.badRequest().body("Failed to upload files lỗi upload: " + e.getMessage());
                }
            }else
            {
                return ResponseEntity.badRequest().body("You have no permission to upload files!");
            }

        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to upload files lỗi server: " + e.getMessage());
        }

    }

    @GetMapping("{macongviec}")
    public ResponseEntity<List<FilesResponse>> getAllFiles(@PathVariable String macongviec) {

        try {
            if(CheckToken.CheckHanToKen(request,authenticationService)){
                List<FilesResponse> files = filesService.getFiles(macongviec);
                return ResponseEntity.ok(files);
            }else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (ParseException  |JOSEException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Resource file = filesService.loadFileAsResource(filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
