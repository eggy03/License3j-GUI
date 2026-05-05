package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseEntityService;
import javax0.license3j.Feature;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * {@link SwingWorker} responsible for adding a {@link Feature} to a {@link LicenseEntity}.
 *
 * <p>Feature addition is delegated to {@link LicenseEntityService}
 * and the resultant {@link LicenseEntity} replaces the current value
 * in a shared {@link AtomicReference}, making it available across
 * the application.</p>
 */
public class LicenseFeatureAdditionWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(LicenseFeatureAdditionWorker.class);

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final LicenseEntityService service;
    private final Feature feature;

    public LicenseFeatureAdditionWorker(
            @NonNull AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull LicenseEntityService service,
            @NonNull Feature feature
    ) {
        this.licenseEntityAtomicReference = Objects.requireNonNull(licenseEntityAtomicReference, "licenseEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
        this.feature = Objects.requireNonNull(feature, "feature cannot be null");
    }

    @Override
    protected String doInBackground() {

        LicenseEntity oldlicenseEntity = licenseEntityAtomicReference.get();

        if (oldlicenseEntity == null || !oldlicenseEntity.hasLicense())
            return "No license is loaded in memory";

        LicenseEntity newLicenseEntity = service.addFeature(oldlicenseEntity, feature);
        licenseEntityAtomicReference.set(newLicenseEntity);

        return String.format("Feature: %s=%s has been added to the license", feature.name(), feature.valueString());
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.warn("Interrupted while trying to add feature [{}={}] to the license", feature.name(), feature.valueString());
            log.debug("Stack trace for interruption", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Failed to add the feature [{}={}] to the license", feature.name(), feature.valueString());
            log.debug("Stack trace for failure", e.getCause());
        }
    }
}
