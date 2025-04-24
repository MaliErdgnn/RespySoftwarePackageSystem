package com.mehmetalierdogan.RepsySoftwarePackageSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "packages")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageEntity {
    @EmbeddedId
    private PackageId id;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "metadata_path", nullable = false)
    private String metaDataPath;

    @Column(name = "package_path", nullable = false)
    private String packagepath;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = false)
    private StorageType storageType;

    public String getName() {
        return id != null ? id.getName() : null;
    }

    public void setName(String name) {
        if (id == null) id = new PackageId();
        id.setName(name);
    }

    public String getVersion() {
        return id != null ? id.getVersion() : null;
    }

    public void setVersion(String version) {
        if (id == null) id = new PackageId();
        id.setVersion(version);
    }

    public PackageId getId() {
        return id;
    }

    public void setId(PackageId id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMetaDataPath() {
        return metaDataPath;
    }

    public void setMetaDataPath(String metaDataPath) {
        this.metaDataPath = metaDataPath;
    }

    public String getPackagepath() {
        return packagepath;
    }

    public void setPackagepath(String packagepath) {
        this.packagepath = packagepath;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }
}
