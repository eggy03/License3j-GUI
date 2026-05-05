package io.github.eggy03.application.entity;

import javax0.license3j.License;
import org.jspecify.annotations.Nullable;

/**
 * Wrapper for a {@link License} instance.
 */
public record LicenseEntity(@Nullable License license) {

    /**
     * Checks whether a {@link License} is present in {@link LicenseEntity}.
     * It is recommended to call this function before fetching the license.
     *
     * @return {@code true} if a license is present.
     */
    public boolean hasLicense() {
        return license != null;
    }

    /**
     * @return {@code true} if a {@link License} is present and has the {@code licenseSignature} feature,
     * {@code false} otherwise
     */
    public boolean hasSignature() {
        return license != null && license.get("licenseSignature") != null;
    }
}