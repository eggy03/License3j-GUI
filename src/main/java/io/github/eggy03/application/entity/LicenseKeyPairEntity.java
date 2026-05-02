package io.github.eggy03.application.entity;

import javax0.license3j.crypto.LicenseKeyPair;
import org.jspecify.annotations.Nullable;

public record LicenseKeyPairEntity(@Nullable LicenseKeyPair licenseKeyPair) {

    public boolean isLoaded() {
        return licenseKeyPair != null;
    }

    public boolean hasPrivateKey() {
        return licenseKeyPair != null
                && licenseKeyPair.getPair() != null
                && licenseKeyPair.getPair().getPrivate() != null;
    }

    public boolean hasPublicKey() {
        return licenseKeyPair != null
                && licenseKeyPair.getPair() != null
                && licenseKeyPair.getPair().getPublic() != null;
    }
}