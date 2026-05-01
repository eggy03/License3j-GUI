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
public class LicenseVerifyWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseEntity> licenseEntity;
    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntity;
    private final LicenseSignService service;
    private final JTextArea textArea;

    @Override
    protected String doInBackground() {

        if(licenseEntity.get().license()==null)
            return "ERROR: No license is loaded in memory";

        if(licenseKeyPairEntity.get().licenseKeyPair()==null)
            return "ERROR: No keys are loaded in memory";

        boolean verified = service.verify(licenseEntity.get(), licenseKeyPairEntity.get());
        return verified ? "INFO: License signature verified" : "WARN: License signature verification failed. Please sign the license again.";
    }

    @Override
    protected void done() {
        try {
            textArea.append(get());
        } catch (InterruptedException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License verify interrupted", e.getCause());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License verify failure", e.getCause());
        }
    }
}
