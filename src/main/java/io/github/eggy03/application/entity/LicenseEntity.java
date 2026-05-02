package io.github.eggy03.application.entity;

import javax0.license3j.License;
import org.jspecify.annotations.Nullable;

public record LicenseEntity(@Nullable License license) {

    public boolean hasLicense() {
        return license != null;
    }

    public boolean hasSignature() {
        return license != null && license.get("licenseSignature") != null;
    }
}