package com.mehmetalierdogan.RepsySoftwarePackageSystem.DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PackageResponseDto {
    private String name;
    private String version;
    private String author;
    private String storageType;
    private String message;



    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getStorageType() { return storageType; }
    public void setStorageType(String storageType) { this.storageType = storageType; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}