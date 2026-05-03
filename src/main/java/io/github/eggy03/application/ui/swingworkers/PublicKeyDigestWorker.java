package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseKeyPairService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class PublicKeyDigestWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(PublicKeyDigestWorker.class);

    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference;
    private final LicenseKeyPairService service;

    public PublicKeyDigestWorker(
            @NonNull AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference,
            @NonNull LicenseKeyPairService service
    ) {
        this.licenseKeyPairEntityAtomicReference = Objects.requireNonNull(licenseKeyPairEntityAtomicReference, "licenseKeyPairEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
    }

    @Override
    protected String doInBackground() {

        LicenseKeyPairEntity licenseKeyPairEntity = licenseKeyPairEntityAtomicReference.get();

        if (licenseKeyPairEntity == null || !licenseKeyPairEntity.hasPublicKey())
            return "Public key has not been loaded in memory";

        return service.digestPublicKey(licenseKeyPairEntity);
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("Public key digest interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Public key digest failure", e.getCause());
        }
    }
}
