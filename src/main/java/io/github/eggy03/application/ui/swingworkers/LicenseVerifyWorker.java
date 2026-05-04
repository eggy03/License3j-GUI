package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseEntityService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class LicenseVerifyWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(LicenseVerifyWorker.class);

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference;
    private final LicenseEntityService service;

    public LicenseVerifyWorker(
            @NonNull AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull LicenseEntityService service
    ) {
        this.licenseEntityAtomicReference = Objects.requireNonNull(licenseEntityAtomicReference, "licenseEntityAtomicReference cannot be null");
        this.licenseKeyPairEntityAtomicReference = Objects.requireNonNull(licenseKeyPairEntityAtomicReference, "licenseKeyPairEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
    }

    @Override
    protected String doInBackground() {

        LicenseEntity licenseEntity = licenseEntityAtomicReference.get();
        LicenseKeyPairEntity licenseKeyPairEntity = licenseKeyPairEntityAtomicReference.get();

        if (licenseEntity == null || !licenseEntity.hasLicense())
            return "No license loaded in memory";

        if (licenseKeyPairEntity == null || !licenseKeyPairEntity.hasPublicKey())
            return "Public key not loaded in memory";

        boolean licenseVerified = service.verifyLicense(licenseEntity, licenseKeyPairEntity);
        return licenseVerified ? "License signature verified" : "License signature invalid";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.warn("Interrupted while verifying license signature");
            log.debug("Stack trace for interruption", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Failed to verify license signature");
            log.debug("Stack trace for failure", e.getCause());
        }
    }
}
