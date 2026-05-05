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

/**
 * {@link SwingWorker} responsible for generating a new {@link LicenseKeyPairEntity}.
 *
 * <p>Generation is delegated to {@link LicenseKeyPairEntityService}
 * and the resultant {@link LicenseKeyPairEntity} replaces the current value
 * in a shared {@link AtomicReference}, making the generated
 * key pair available across the application.</p>
 */
public class KeyPairGenerationWorker extends SwingWorker<Void, Void> {

    private static final Logger log = LoggerFactory.getLogger(KeyPairGenerationWorker.class);

    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference;
    private final LicenseKeyPairEntityService service;
    private final String cipher;
    private final int size;

    public KeyPairGenerationWorker(
            @NonNull AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull LicenseKeyPairEntityService service,
            @NonNull String cipher,
            int size

    ) {
        this.licenseKeyPairEntityAtomicReference = Objects.requireNonNull(licenseKeyPairEntityAtomicReference, "licenseKeyPairEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
        this.cipher = Objects.requireNonNull(cipher, "cipher cannot be null");
        this.size = size;
    }

    @Override
    protected Void doInBackground() {

        LicenseKeyPairEntity newLicenseKeyPairEntity = service.generateLicenseKeyPair(cipher, size);
        licenseKeyPairEntityAtomicReference.set(newLicenseKeyPairEntity);

        return null;
    }

    @Override
    protected void done() {
        try {
            get();
            log.info("Key pair generation completed successfully using [cipher={}, size={}]", cipher, size);
        } catch (InterruptedException e) {
            log.warn("Interrupted while generating key pair using [cipher={}, size={}]", cipher, size);
            log.debug("Stack trace for interruption", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Failed to generate key pair using [cipher={}, size={}]", cipher, size);
            log.debug("Stack trace for failure", e.getCause());
        }
    }
}
