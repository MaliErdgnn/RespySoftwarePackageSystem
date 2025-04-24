package com.mehmetalierdogan.RepsySoftwarePackageSystem.Services;

import com.mehmetalierdogan.RepsySoftwarePackageSystem.DataTransferObjects.PackageMetaData;
import com.mehmetalierdogan.RepsySoftwarePackageSystem.DataTransferObjects.PackageResponseDto;
import com.mehmetalierdogan.RepsySoftwarePackageSystem.DataAccessLayer.PackageRepository;
import com.mehmetalierdogan.RepsySoftwarePackageSystem.Entity.PackageEntity;
import com.mehmetalierdogan.RepsySoftwarePackageSystem.Entity.PackageId;
import com.mehmetalierdogan.RepsySoftwarePackageSystem.Entity.StorageType;
import com.mehmeterdogan.storage.StorageStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class StorageService {
    private final StorageStrategy strategy;
    private final PackageRepository repo;

    public StorageService(StorageStrategy strategy, PackageRepository repo) {
        this.strategy = strategy;
        this.repo = repo;
    }

    public PackageResponseDto deploy(String name, String version, MultipartFile rep, MultipartFile meta) throws IOException {
        if (!rep.getOriginalFilename().toLowerCase().endsWith(".rep")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The package provided must have .rep extension.");
        }

        if (!meta.getOriginalFilename().toLowerCase().endsWith(".json")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The metadata file provided must have .json extension.");
        }
        PackageId packageId = new PackageId(name, version);
        if (repo.existsById(packageId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This package version already exists.");
        }

        PackageMetaData metadata;
        try {
            ObjectMapper mapper = new ObjectMapper();
            metadata = mapper.readValue(meta.getInputStream(), PackageMetaData.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid meta.json file.");
        }

        if (metadata.getName() == null || metadata.getVersion() == null || metadata.getAuthor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields in meta.json (name, version, author).");
        }

        if (!metadata.getName().equals(name) || !metadata.getVersion().equals(version)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Metadata name/version does not match path parameters.");
        }

        String storagePath;
        try {
            storagePath = strategy.store(name, version, rep, meta);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store files.");
        }

        try {
            PackageEntity entity = new PackageEntity();
            entity.setId(packageId);
            entity.setAuthor(metadata.getAuthor());
            entity.setMetaDataPath(storagePath + "/meta.json");
            entity.setPackagepath(storagePath + "/package.rep");
            entity.setStorageType(StorageType.valueOf(strategy.getStorageTypeName()));
            repo.save(entity);
            return new PackageResponseDto(name, version, metadata.getAuthor(), entity.getStorageType().name(), "Package deployed successfully.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Package is stored but failed to save it in database.");
        }
    }
    public Resource download(String name, String version, String fileName) throws IOException {
        try {
            List<Resource> files = strategy.load(name, version, fileName);
            if (files.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }
            return files.get(0);
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested file not found.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File loading failed.");
        }
    }



}