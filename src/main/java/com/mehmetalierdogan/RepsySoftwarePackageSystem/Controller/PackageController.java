package com.mehmetalierdogan.RepsySoftwarePackageSystem.Controller;

import com.mehmetalierdogan.RepsySoftwarePackageSystem.DataTransferObjects.PackageResponseDto;
import com.mehmetalierdogan.RepsySoftwarePackageSystem.Services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class PackageController {
    private final StorageService service;

    public PackageController(StorageService service) {
        this.service = service;
    }

    @PostMapping("{name}/{version}")
    public ResponseEntity<PackageResponseDto> deploy(
            @PathVariable String name,
            @PathVariable String version,
            @RequestParam("package.rep") MultipartFile rep,
            @RequestParam("meta.json") MultipartFile meta
    ) throws IOException {
        PackageResponseDto response = service.deploy(name, version, rep, meta);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{name}/{version}/{filename}")
    public ResponseEntity<Resource> download(
            @PathVariable String name,
            @PathVariable String version,
            @PathVariable String filename
    ) throws IOException {
        Resource file = service.download(name, version, filename);

        String contentType;
        if (filename.endsWith(".json")) {
            contentType = "application/json";
        } else if (filename.endsWith(".rep") || filename.endsWith(".zip")) {
            contentType = "application/zip";
        } else {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }
}
