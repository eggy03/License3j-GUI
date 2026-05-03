package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseGenerationService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class LicenseGenerationWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(LicenseGenerationWorker.class);

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final LicenseGenerationService service;

    public LicenseGenerationWorker(
            @NonNull AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull LicenseGenerationService service
    ) {
        this.licenseEntityAtomicReference = Objects.requireNonNull(licenseEntityAtomicReference, "licenseEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
    }

    @Override
    protected String doInBackground() {
        LicenseEntity newLicenseEntity = service.generateLicense();
        licenseEntityAtomicReference.set(newLicenseEntity);
        return "A new license has been generated in memory";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("License generation interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("License generation failure", e.getCause());
        }
    }
}
