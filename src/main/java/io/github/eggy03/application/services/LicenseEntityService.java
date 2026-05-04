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

// todo improve exception messages
@SuppressWarnings("java:S1192")
public class LicenseEntityService {

    private static final int MAX_LICENSE_SIZE = 20 * 1024 * 1024;

    @NonNull
    public LicenseEntity generateLicense() {
        return new LicenseEntity(new License());
    }

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

    public boolean verifyLicense(@NonNull LicenseEntity licenseEntity, @NonNull LicenseKeyPairEntity licenseKeyPairEntity) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(licenseKeyPairEntity, "licenseKeyPairEntity cannot be null");

        LicenseKeyPair licenseKeyPair = Objects.requireNonNull(licenseKeyPairEntity.licenseKeyPair(), "licenseKeyPair cannot be null");
        KeyPair keyPair = Objects.requireNonNull(licenseKeyPair.getPair(), "keyPair cannot be null");
        PublicKey publicKey = Objects.requireNonNull(keyPair.getPublic(), "publicKey cannot be null");

        License license = Objects.requireNonNull(licenseEntity.license(), "license cannot be null");

        return license.isOK(publicKey);
    }

    @NonNull
    public LicenseEntity saveLicense(@NonNull LicenseEntity licenseEntity, @NonNull File licenseFolder, @NonNull String licenseName, @NonNull IOFormat licenseFormat) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(licenseFolder, "licenseFolder cannot be null");
        Objects.requireNonNull(licenseName, "licenseName cannot be null");
        Objects.requireNonNull(licenseFormat, "licenseFormat cannot be null");

        if (!licenseFolder.isDirectory()) {
            throw new LicenseSaveException("Provided license folder:" + licenseFolder.getPath() + "is not a directory");
        }

        if (!licenseName.matches("^[a-zA-Z0-9._-]+$")) {
            throw new LicenseSaveException("License name:" + licenseName + "contains invalid characters");
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
