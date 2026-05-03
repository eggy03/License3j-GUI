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

public class KeyPairLoaderWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(KeyPairLoaderWorker.class);

    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference;
    private final LicenseKeyPairEntityService service;
    private final IOFormat keyFormat;
    private final File privateKeyFile;
    private final File publicKeyFile;

    public KeyPairLoaderWorker(
            @NonNull AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull LicenseKeyPairEntityService service,
            @NonNull IOFormat keyFormat,
            @NonNull File privateKeyFile,
            @NonNull File publicKeyFile
    ) {
        this.licenseKeyPairEntityAtomicReference = Objects.requireNonNull(licenseKeyPairEntityAtomicReference, "licenseKeyPairEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
        this.keyFormat = Objects.requireNonNull(keyFormat, "keyFormat cannot be null");
        this.privateKeyFile = Objects.requireNonNull(privateKeyFile, "privateKeyFile cannot be null");
        this.publicKeyFile = Objects.requireNonNull(publicKeyFile, "publicKeyFile cannot be null");
    }

    @Override
    protected String doInBackground() {
        LicenseKeyPairEntity newLicenseKeyPairEntity = service.loadLicenseKeyPair(privateKeyFile, publicKeyFile, keyFormat);
        licenseKeyPairEntityAtomicReference.set(newLicenseKeyPairEntity);
        return "Keys have been loaded in memory.";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("Key load interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Key load failure", e.getCause());
        }
    }
}
