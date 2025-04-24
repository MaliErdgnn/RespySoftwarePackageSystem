package com.mehmetalierdogan.RepsySoftwarePackageSystem.DataTransferObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PackageMetaData {
    private String name;
    private String version;
    private String author;
    private List<Dependency> dependencies;

    @Data
    public static class Dependency {
        @JsonProperty("package")
        private String packageName;
        private String version;
    }
}
