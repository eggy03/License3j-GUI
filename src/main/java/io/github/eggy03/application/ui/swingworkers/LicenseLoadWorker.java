package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseGenerationService;
import javax0.license3j.io.IOFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingWorker;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
public class LicenseLoadWorker extends SwingWorker<String, Void> {

    private final File licenseFile;
    private final IOFormat licenseFormat;
    private final AtomicReference<LicenseEntity> licenseEntity;
    private final LicenseGenerationService service;

    @Override
    protected String doInBackground() {
        licenseEntity.set(service.loadLicense(licenseFile, licenseFormat));
        return "An existing license has been loaded in memory";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("License load interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("License load failure", e);
        }
    }
}
