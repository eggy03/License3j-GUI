package io.github.eggy03.application.workers;

import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseKeyPairService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingWorker;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
public class KeyPairGenerationWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntity;
    private final String cipher;
    private final int size;
    private final LicenseKeyPairService service;

    @Override
    protected String doInBackground() {
        licenseKeyPairEntity.set(service.generateKeyPair(cipher, size));
        return "Keys have been generated in memory.";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("Key generation interrupted", e.getCause());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            
            log.error("Key generation failure", e.getCause());
        }
    }
}
