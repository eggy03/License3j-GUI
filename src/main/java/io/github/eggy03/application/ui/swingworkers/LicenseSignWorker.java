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

public class LicenseSignWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(LicenseSignWorker.class);

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference;
    private final LicenseEntityService service;
    private final String signatureDigest;

    public LicenseSignWorker(
            @NonNull AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull LicenseEntityService service,
            @NonNull String signatureDigest
    ) {
        this.licenseEntityAtomicReference = Objects.requireNonNull(licenseEntityAtomicReference, "licenseEntityAtomicReference cannot be null");
        this.licenseKeyPairEntityAtomicReference = Objects.requireNonNull(licenseKeyPairEntityAtomicReference, "licenseKeyPairEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
        this.signatureDigest = Objects.requireNonNull(signatureDigest, "signatureDigest cannot be null");
    }

    @Override
    protected String doInBackground() {

        LicenseEntity unsignedLicenseEntity = licenseEntityAtomicReference.get();
        LicenseKeyPairEntity licenseKeyPairEntity = licenseKeyPairEntityAtomicReference.get();

        if (unsignedLicenseEntity == null || !unsignedLicenseEntity.hasLicense())
            return "No license is loaded in memory";

        if (licenseKeyPairEntity == null || !licenseKeyPairEntity.hasPrivateKey())
            return "Private key has not been loaded in memory";

        LicenseEntity signedLicenseEntity = service.signLicense(unsignedLicenseEntity, licenseKeyPairEntity, signatureDigest);
        licenseEntityAtomicReference.set(signedLicenseEntity);
        return "License has been signed. Please save your license and keys before exiting";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {

            log.error("License sign interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {

            log.error("License sign failure", e.getCause());
        }
    }
}
