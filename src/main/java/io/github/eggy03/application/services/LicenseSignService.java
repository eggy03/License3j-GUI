package io.github.eggy03.application.services;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.exception.LicenseSaveException;
import io.github.eggy03.application.exception.LicenseSignException;
import javax0.license3j.License;
import javax0.license3j.crypto.LicenseKeyPair;
import javax0.license3j.io.IOFormat;
import javax0.license3j.io.LicenseWriter;
import org.jspecify.annotations.NonNull;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

@SuppressWarnings("java:S1192")
public class LicenseSignService {

    private static final String DIGEST = "SHA-512";

    @NonNull
    public LicenseEntity sign(@NonNull LicenseEntity licenseEntity, @NonNull LicenseKeyPairEntity licenseKeyPairEntity) {

        Objects.requireNonNull(licenseEntity, "licenseEntity cannot be null");
        Objects.requireNonNull(licenseKeyPairEntity, "licenseKeyPairEntity cannot be null");

        LicenseKeyPair licenseKeyPair = Objects.requireNonNull(licenseKeyPairEntity.licenseKeyPair(), "licenseKeyPair cannot be null");
        KeyPair keyPair = Objects.requireNonNull(licenseKeyPair.getPair(), "keyPair cannot be null");
        PrivateKey privateKey = Objects.requireNonNull(keyPair.getPrivate(), "privateKey cannot be null");

        License orignalLicense = Objects.requireNonNull(licenseEntity.license(), "originalLicense cannot be null");
        License copyLicense = License.Create.from(orignalLicense.serialized());

        try {
            copyLicense.sign(privateKey, DIGEST);
            return new LicenseEntity(copyLicense, true, licenseEntity.isSaved());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new LicenseSignException("License sign failure", e);
        }
    }

    public boolean verify(@NonNull LicenseEntity licenseEntity, @NonNull LicenseKeyPairEntity licenseKeyPairEntity) {

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

        License originalLicense = Objects.requireNonNull(licenseEntity.license(), "licenseEntity.license() cannot be null");
        License copyLicense = License.Create.from(originalLicense.serialized());

        try (LicenseWriter licenseWriter = new LicenseWriter(new File(licenseFolder, licenseName))) {
            licenseWriter.write(copyLicense, licenseFormat);
            return new LicenseEntity(copyLicense, licenseEntity.isSigned(), true);

        } catch (IOException e) {
            throw new LicenseSaveException("License save failure", e);
        }

    }
}
