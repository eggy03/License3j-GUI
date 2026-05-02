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
public class PublicKeyDigestWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntity;
    private final LicenseKeyPairService service;

    @Override
    protected String doInBackground() {
        if(licenseKeyPairEntity.get().licenseKeyPair()==null)
            return "License keys have not been loaded in memory";

        return service.digestPublicKey(licenseKeyPairEntity.get());
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("Public key digest interrupted", e.getCause());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Public key digest failure", e.getCause());
        }
    }
}
