package io.github.eggy03.application.workers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseSignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
public class LicenseSignWorker extends SwingWorker<Void, Void> {

    private final AtomicReference<LicenseEntity> licenseEntity;
    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntity;
    private final LicenseSignService service;
    private final JTextArea textArea;

    @Override
    protected Void doInBackground() {
        licenseEntity.set(service.sign(licenseEntity.get(), licenseKeyPairEntity.get()));
        return null;
    }

    @Override
    protected void done() {
        try {
            get();
            textArea.setText("INFO: License has been signed. Please save your license and keys before exiting");
        } catch (InterruptedException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License sign interrupted", e.getCause());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License sign failure", e.getCause());
        }
    }
}
