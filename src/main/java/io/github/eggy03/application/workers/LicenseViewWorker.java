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
public class LicenseViewWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseEntity> licenseEntity;
    private final LicenseGenerationService service;
    private final JTextArea textArea;


    @Override
    protected String doInBackground() {

        if(licenseEntity.get().license()==null)
            return "ERROR: No license is loaded in memory";

        return service.viewLicense(licenseEntity.get());
    }

    @Override
    protected void done() {
        try {
            textArea.append(get());
        } catch (InterruptedException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License view interrupted", e.getCause());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("License view failure", e.getCause());
        }

    }
}
