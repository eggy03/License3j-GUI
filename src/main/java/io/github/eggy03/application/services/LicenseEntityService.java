package io.github.eggy03.application.services;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.exception.LicenseReadException;
import io.github.eggy03.application.exception.LicenseSaveException;
import io.github.eggy03.application.exception.LicenseSignException;
import io.github.eggy03.application.exception.LicenseViewException;
import javax0.license3j.Feature;
import javax0.license3j.License;
import javax0.license3j.crypto.LicenseKeyPair;
import javax0.license3j.io.IOFormat;
import javax0.license3j.io.LicenseReader;
import javax0.license3j.io.LicenseWriter;
import org.jspecify.annotations.NonNull;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

/**
 * Service layer for {@link LicenseEntity} operations.
 *
 * <p>Provides functionality to create, load, modify, view, sign,
 * verify, and persist {@link License} data using the License3j library.</p>
 *
 * <p>All operations are performed on defensive copies of the underlying
 * {@link License} to preserve immutability.</p>
 *
 * <p>Failures are wrapped in domain-specific runtime exceptions.</p>
 */
@SuppressWarnings("java:S1192")
public class LicenseEntityService {

    private static final int MAX_LICENSE_SIZE = 20 * 1024 * 1024;

    /**
     * Creates a new empty {@link License} and wraps it up in a new {@link LicenseEntity} instance.
     *
     * @return a new {@link LicenseEntity} instance
     */
    @NonNull
    public LicenseEntity generateLicense() {
        return new LicenseEntity(new License());
    }

    /**
     * Loads a license from the specified file.
     *
     * @param licenseToLoad the license file to load
     * @param licenseFormat the format of the license
     * @return the loaded {@link License} wrapped in a new {@link LicenseEntity} instance
     * @throws LicenseReadException if reading fails
     */
    @NonNull
    public LicenseEntity loadLicense(@NonNull File licenseToLoad, @NonNull IOFormat licenseFormat) {

        Objects.requireNonNull(licenseToLoad, "licenseToLoad cannot be null");
        Objects.requireNonNull(licenseFormat, "licenseFormat cannot be null");

        try (LicenseReader licenseReader = new LicenseReader(licenseToLoad, MAX_LICENSE_SIZE)) {
            return new LicenseEntity(licenseReader.readChecking(licenseFormat));
        } catch (IOException e) {
            throw new LicenseReadException("License read failure", e);
        }

    }

    /**
     * Adds a feature to the given license.
     *
     * <p>The original license is not mutated; a modified copy is returned.</p>
     *
     * @param licenseEntity the license to modify
     * @param feature       the feature to add
     * @return a new {@link LicenseEntity} with the feature added
     */
    @NonNull
    public LicenseEntity addFeature(@NonNull LicenseEntity licenseEntity, @NonNull Feature feature) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(feature, "feature cannot be null");

        License originalLicense = Objects.requireNonNull(licenseEntity.license(), "license cannot be null");
        License copyLicense = License.Create.from(originalLicense.serialized());

        // mutation operation on copied license
        copyLicense.add(feature);
        return new LicenseEntity(copyLicense);
    }

    /**
     * Serializes the license into a string representation.
     *
     * @param licenseEntity the license to view
     * @return the license as a formatted string
     * @throws LicenseViewException if serialization fails
     */
    @NonNull
    public String viewLicense(@NonNull LicenseEntity licenseEntity) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(licenseEntity.license(), "license cannot be null");

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); LicenseWriter lw = new LicenseWriter(outputStream)) {
            lw.write(licenseEntity.license(), IOFormat.STRING);
            return outputStream.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new LicenseViewException("License view failure", e);
        }
    }

    /**
     * Signs the license using the provided key pair and digest algorithm.
     *
     * <p>The original license is not mutated; a signed copy is returned.</p>
     *
     * @param licenseEntity        the license to sign
     * @param licenseKeyPairEntity the key pair used for signing
     * @param signatureDigest      the signature algorithm (e.g., SHA512)
     * @return a new signed {@link LicenseEntity}
     * @throws LicenseSignException if signing fails
     */
    @NonNull
    public LicenseEntity signLicense(@NonNull LicenseEntity licenseEntity, @NonNull LicenseKeyPairEntity licenseKeyPairEntity, @NonNull String signatureDigest) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(licenseKeyPairEntity, "licenseKeyPairEntity cannot be null");

        LicenseKeyPair licenseKeyPair = Objects.requireNonNull(licenseKeyPairEntity.licenseKeyPair(), "licenseKeyPair cannot be null");
        KeyPair keyPair = Objects.requireNonNull(licenseKeyPair.getPair(), "keyPair cannot be null");
        PrivateKey privateKey = Objects.requireNonNull(keyPair.getPrivate(), "privateKey cannot be null");

        License orignalLicense = Objects.requireNonNull(licenseEntity.license(), "originalLicense cannot be null");
        License copyLicense = License.Create.from(orignalLicense.serialized());

        try {
            copyLicense.sign(privateKey, Objects.requireNonNull(signatureDigest, "signatureDigest cannot be null"));
            return new LicenseEntity(copyLicense);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException e) {
            throw new LicenseSignException("License sign failure", e);
        }
    }

    /**
     * Verifies the signature of the license using the provided public key.
     *
     * @param licenseEntity        the license to verify
     * @param licenseKeyPairEntity the key pair containing the public key
     * @return {@code true} if the signature is valid, otherwise {@code false}
     */
    public boolean verifyLicense(@NonNull LicenseEntity licenseEntity, @NonNull LicenseKeyPairEntity licenseKeyPairEntity) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(licenseKeyPairEntity, "licenseKeyPairEntity cannot be null");

        LicenseKeyPair licenseKeyPair = Objects.requireNonNull(licenseKeyPairEntity.licenseKeyPair(), "licenseKeyPair cannot be null");
        KeyPair keyPair = Objects.requireNonNull(licenseKeyPair.getPair(), "keyPair cannot be null");
        PublicKey publicKey = Objects.requireNonNull(keyPair.getPublic(), "publicKey cannot be null");

        License license = Objects.requireNonNull(licenseEntity.license(), "license cannot be null");

        return license.isOK(publicKey);
    }

    /**
     * Saves the license to the specified folder using the given name and format.
     *
     * <p>The original license is not mutated; a saved copy is returned.</p>
     *
     * @param licenseEntity the license to save
     * @param licenseFolder the target directory
     * @param licenseName   the file name (validated)
     * @param licenseFormat the output format
     * @return a new {@link LicenseEntity} representing the saved license
     * @throws LicenseSaveException if validation or saving fails
     */
    @NonNull
    public LicenseEntity saveLicense(@NonNull LicenseEntity licenseEntity, @NonNull File licenseFolder, @NonNull String licenseName, @NonNull IOFormat licenseFormat) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(licenseFolder, "licenseFolder cannot be null");
        Objects.requireNonNull(licenseName, "licenseName cannot be null");
        Objects.requireNonNull(licenseFormat, "licenseFormat cannot be null");

        if (!licenseFolder.isDirectory()) {
            throw new LicenseSaveException(String.format("Provided license folder [%s] is not a directory", licenseFolder));
        }

        if (!licenseName.matches("^[a-zA-Z0-9._-]+$")) {
            throw new LicenseSaveException(String.format("License name '%s' contains invalid characters", licenseName));
        }

        License originalLicense = Objects.requireNonNull(licenseEntity.license(), "license cannot be null");
        License copyLicense = License.Create.from(originalLicense.serialized());

        try (LicenseWriter licenseWriter = new LicenseWriter(new File(licenseFolder, licenseName))) {
            licenseWriter.write(copyLicense, licenseFormat);
            return new LicenseEntity(copyLicense);

        } catch (IOException e) {
            throw new LicenseSaveException("License save failure", e);
        }

    }

}
