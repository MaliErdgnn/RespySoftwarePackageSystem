package com.mehmetalierdogan.RepsySoftwarePackageSystem.Entity;

public enum StorageType {
    ObjectStorage(0),
    FileStreamStorage(1),
    ;
    private final int value;

    StorageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
