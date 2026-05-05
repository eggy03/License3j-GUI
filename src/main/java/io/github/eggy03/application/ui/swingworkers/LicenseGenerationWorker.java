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

/**
 * {@link SwingWorker} responsible for generating a new {@link LicenseEntity}.
 *
 * <p>Generation is delegated to {@link LicenseEntityService}
 * and the resultant {@link LicenseEntity} replaces the current value
 * in a shared {@link AtomicReference}, making it available across the application.</p>
 */
public class LicenseGenerationWorker extends SwingWorker<Void, Void> {

    private static final Logger log = LoggerFactory.getLogger(LicenseGenerationWorker.class);

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final LicenseEntityService service;

    public LicenseGenerationWorker(
            @NonNull AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull LicenseEntityService service
    ) {
        this.licenseEntityAtomicReference = Objects.requireNonNull(licenseEntityAtomicReference, "licenseEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
    }

    @Override
    protected Void doInBackground() {
        LicenseEntity newLicenseEntity = service.generateLicense();
        licenseEntityAtomicReference.set(newLicenseEntity);
        return null;
    }

    @Override
    protected void done() {
        try {
            get();
            log.info("Fresh license generated in memory");
        } catch (InterruptedException e) {
            log.warn("Interrupted while generating license in memory");
            log.debug("Stack trace for interruption", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Failed to generate license in memory");
            log.debug("Stack trace for failure", e.getCause());
        }
    }
}
