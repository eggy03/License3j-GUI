package io.github.eggy03.application.workers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseSignService;
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
public class LicenseSaveWorker extends SwingWorker<Void, Void> {

    private final AtomicReference<LicenseEntity> licenseEntity;
    private final File licenseFolder;
    private final String licenseName;
    private final IOFormat licenseFormat;
    private final LicenseSignService service;
    private final JTextArea textArea;

    @Override
    protected Void doInBackground() {
        licenseEntity.set(service.saveLicense(licenseEntity.get(), licenseFolder, licenseName, licenseFormat));
        return null;
    }

    @Override
    protected void done() {
        try {
            get();
            textArea.setText("INFO: License has saved. Don't forget to save your keys before exiting.");
        } catch (InterruptedException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License save interrupted", e.getCause());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License save failure", e.getCause());
        }
    }
}
