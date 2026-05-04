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
        return "Feature " + feature.name() + "=" + feature.valueString() + "added to the license";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("License feature add interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("License feature add failure", e.getCause());
        }

    }
}
