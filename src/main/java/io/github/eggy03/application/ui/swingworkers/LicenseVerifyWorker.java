package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseSignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingWorker;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
public class LicenseVerifyWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference;
    private final LicenseSignService service;

    @Override
    protected String doInBackground() {

        LicenseEntity licenseEntity = licenseEntityAtomicReference.get();
        LicenseKeyPairEntity licenseKeyPairEntity = licenseKeyPairEntityAtomicReference.get();

        if(!licenseEntity.hasLicense())
            return "No license is loaded in memory";

        if(!licenseKeyPairEntity.hasPublicKey())
            return "Public key has not been loaded in memory";

        boolean verified = service.verify(licenseEntity, licenseKeyPairEntity);
        return verified ? "License signature verified" : "License signature verification failed. Please sign the license again.";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("License verify interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("License verify failure", e);
        }
    }
}
