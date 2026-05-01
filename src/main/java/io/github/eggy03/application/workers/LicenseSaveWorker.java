package io.github.eggy03.application.workers;

import io.github.eggy03.application.entity.LicenseEntity;
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
public class LicenseSaveWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseEntity> licenseEntity;
    private final File licenseFolder;
    private final String licenseName;
    private final IOFormat licenseFormat;
    private final LicenseSignService service;
    private final JTextArea textArea;

    @Override
    protected String doInBackground() {
        if(licenseEntity.get().license()==null)
            return "ERROR: No license is loaded in memory";

        licenseEntity.set(service.saveLicense(licenseEntity.get(), licenseFolder, licenseName, licenseFormat));
        return "INFO: License has saved. Don't forget to save your keys before exiting.";
    }

    @Override
    protected void done() {
        try {
            textArea.setText(get());
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
