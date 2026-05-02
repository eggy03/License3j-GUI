package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseGenerationService;
import javax0.license3j.Feature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingWorker;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
public class LicenseFeatureAdditionWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseEntity> licenseEntity;
    private final Feature feature;
    private final LicenseGenerationService service;

    @Override
    protected String doInBackground() {

        if(licenseEntity.get().license()==null)
            return "No license is loaded in memory";

        licenseEntity.set(service.addFeature(licenseEntity.get(), feature));
        return "Feature" + feature.name() + "=" + feature.valueString() + "added to the license";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("License feature add interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("License feature add failure", e);
        }

    }
}
