package io.github.eggy03.application.entity;

import javax0.license3j.crypto.LicenseKeyPair;
import org.jspecify.annotations.Nullable;

/**
 * Wrapper for a {@link LicenseKeyPair} instance.
 */
public record LicenseKeyPairEntity(@Nullable LicenseKeyPair licenseKeyPair) {

    /**
     * @return {@code true} if a key pair is loaded in memory.
     */
    public boolean isLoaded() {
        return licenseKeyPair != null;
    }

    /**
     * @return {@code true} if a private key is available.
     */
    public boolean hasPrivateKey() {
        return licenseKeyPair != null
                && licenseKeyPair.getPair() != null
                && licenseKeyPair.getPair().getPrivate() != null;
    }

    /**
     * @return {@code true} if a public key is available.
     */
    public boolean hasPublicKey() {
        return licenseKeyPair != null
                && licenseKeyPair.getPair() != null
                && licenseKeyPair.getPair().getPublic() != null;
    }
}