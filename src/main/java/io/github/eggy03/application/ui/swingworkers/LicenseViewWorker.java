package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseEntityService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class LicenseViewWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(LicenseViewWorker.class);

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final LicenseEntityService service;

    public LicenseViewWorker(
            @NonNull AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull LicenseEntityService service
    ) {
        this.licenseEntityAtomicReference = Objects.requireNonNull(licenseEntityAtomicReference, "licenseEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
    }

    @Override
    protected String doInBackground() {

        LicenseEntity licenseEntity = licenseEntityAtomicReference.get();

        if (licenseEntity == null || !licenseEntity.hasLicense())
            return "No license loaded in memory";

        return service.viewLicense(licenseEntity);
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.warn("Interrupted while displaying license content");
            log.debug("Stack trace for interruption", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Failed to display license content");
            log.debug("Stack trace for failure", e.getCause());
        }

    }
}
