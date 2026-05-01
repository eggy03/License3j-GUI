package io.github.eggy03.application.entity;

import javax0.license3j.crypto.LicenseKeyPair;
import org.jspecify.annotations.Nullable;

public record LicenseKeyPairEntity(@Nullable LicenseKeyPair licenseKeyPair, boolean isLoaded, boolean isSaved) {
}
