package io.github.eggy03.application.entity;

import javax0.license3j.crypto.LicenseKeyPair;
import org.jspecify.annotations.NonNull;

public record LicenseKeyPairEntity(@NonNull LicenseKeyPair licenseKeyPair, boolean isLoaded, boolean isSaved) {
}
