package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseKeyPairEntityService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class KeyPairGenerationWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(KeyPairGenerationWorker.class);

    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference;
    private final LicenseKeyPairEntityService service;
    private final String cipher;
    private final int size;

    public KeyPairGenerationWorker(
            @NonNull AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull String cipher,
            int size,
            @NonNull LicenseKeyPairEntityService service
    ) {
        this.licenseKeyPairEntityAtomicReference = Objects.requireNonNull(licenseKeyPairEntityAtomicReference, "licenseKeyPairEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
        this.cipher = Objects.requireNonNull(cipher, "cipher cannot be null");
        this.size = size;
    }

    @Override
    protected String doInBackground() {
        LicenseKeyPairEntity newLicenseKeyPairEntity = service.generateLicenseKeyPair(cipher, size);
        licenseKeyPairEntityAtomicReference.set(newLicenseKeyPairEntity);
        return "Keys have been generated in memory.";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("Key generation interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Key generation failure", e.getCause());
        }
    }
}
