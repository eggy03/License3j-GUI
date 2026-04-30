package io.github.eggy03.application.workers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseGenerationService;
import javax0.license3j.io.IOFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
public class LicenseLoadWorker extends SwingWorker<Void, Void> {

    private final File licenseFile;
    private final IOFormat licenseFormat;
    private final AtomicReference<LicenseEntity> licenseEntity;
    private final LicenseGenerationService service;
    private final JTextArea textArea;

    @Override
    protected Void doInBackground() {
        licenseEntity.set(service.loadLicense(licenseFile, licenseFormat));
        return null;
    }

    @Override
    protected void done() {
        try {
            get();
            textArea.append("INFO: An existing license has been loaded in memory");
        } catch (InterruptedException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License load interrupted", e.getCause());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License load failure", e.getCause());
        }
    }
}
