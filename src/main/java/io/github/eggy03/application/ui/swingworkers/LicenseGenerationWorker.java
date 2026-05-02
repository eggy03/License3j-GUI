package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingWorker;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
public class LicenseGenerationWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final LicenseGenerationService service;

    @Override
    protected String doInBackground() {
        licenseEntityAtomicReference.set(service.generateLicense());
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
            log.error("License generation failure", e);
        }
    }
}
