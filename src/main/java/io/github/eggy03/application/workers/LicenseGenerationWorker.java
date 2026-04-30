package io.github.eggy03.application.workers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
public class LicenseGenerationWorker extends SwingWorker<Void, Void> {

    private final AtomicReference<LicenseEntity> licenseEntity;
    private final LicenseGenerationService service;
    private final JTextArea textArea;

    @Override
    protected Void doInBackground() {
        licenseEntity.set(service.generateLicense());
        return null;
    }

    @Override
    protected void done() {
        try {
            get();
            textArea.append("INFO: A new license has been generated in memory");
        } catch (InterruptedException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License generation interrupted", e.getCause());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License generation failure", e.getCause());
        }
    }
}
