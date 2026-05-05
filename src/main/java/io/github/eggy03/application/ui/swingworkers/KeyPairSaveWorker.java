package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseKeyPairEntityService;
import javax0.license3j.io.IOFormat;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * {@link SwingWorker} responsible for saving a {@link LicenseKeyPairEntity}.
 *
 * <p>Saving is delegated to {@link LicenseKeyPairEntityService}
 * and the resultant {@link LicenseKeyPairEntity} replaces the current value
 * in a shared {@link AtomicReference}</p>
 */
public class KeyPairSaveWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(KeyPairSaveWorker.class);

    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference;
    private final LicenseKeyPairEntityService service;
    private final String privateKeyName;
    private final String publicKeyName;
    private final IOFormat keyFormat;
    private final File keyFolder;

    public KeyPairSaveWorker(
            @NonNull AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull LicenseKeyPairEntityService service,
            @NonNull String privateKeyName,
            @NonNull String publicKeyName,
            @NonNull IOFormat keyFormat,
            @NonNull File keyFolder
    ) {
        this.licenseKeyPairEntityAtomicReference = Objects.requireNonNull(licenseKeyPairEntityAtomicReference, "licenseKeyPairEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
        this.privateKeyName = Objects.requireNonNull(privateKeyName, "privateKeyName cannot be null");
        this.publicKeyName = Objects.requireNonNull(publicKeyName, "publicKeyName cannot be null");
        this.keyFormat = Objects.requireNonNull(keyFormat, "keyFormat cannot be null");
        this.keyFolder = Objects.requireNonNull(keyFolder, "keyFolder cannot be null");
    }

    @Override
    protected String doInBackground() {

        LicenseKeyPairEntity unsavedLicenseKeyPairEntity = licenseKeyPairEntityAtomicReference.get();

        if (unsavedLicenseKeyPairEntity == null)
            return "Keys have not been loaded in memory";

        if (!unsavedLicenseKeyPairEntity.hasPublicKey())
            return "Public key has not been loaded in memory";

        if (!unsavedLicenseKeyPairEntity.hasPrivateKey())
            return "Private key has not been loaded in memory";

        LicenseKeyPairEntity savedLicenseKeyPairEntity = service.saveKeys(unsavedLicenseKeyPairEntity, keyFormat, privateKeyName, publicKeyName, keyFolder);
        licenseKeyPairEntityAtomicReference.set(savedLicenseKeyPairEntity);
        return String.format("Key pair [privateKeyName=%s, publicKeyName=%s, format=%s] saved to [folder=%s]", privateKeyName, publicKeyName, keyFormat, keyFolder);

    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.warn("Interrupted while saving key pair [privateKeyName={}, publicKeyName={}, format={}] to [folder={}]", privateKeyName, publicKeyName, keyFormat, keyFolder);
            log.debug("Stack trace for interruption", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Failed to save key pair [privateKeyName={}, publicKeyName={}, format={}] to [folder={}]", privateKeyName, publicKeyName, keyFormat, keyFolder);
            log.debug("Stack trace for failure", e.getCause());
        }
    }
}
