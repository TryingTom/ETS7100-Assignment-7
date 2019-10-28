package com.example.assignment7;

public class urlOwnerLicense {
    private String file;
    private String license;
    private String owner;

    public urlOwnerLicense(String file, String license, String owner) {
        this.file = file;
        this.license = license;
        this.owner = owner;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
