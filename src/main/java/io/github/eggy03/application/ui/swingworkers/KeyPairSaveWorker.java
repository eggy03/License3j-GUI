package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseKeyPairService;
import javax0.license3j.io.IOFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingWorker;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
public class KeyPairSaveWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntity;
    private final File keyFolder;
    private final String privateKeyName;
    private final String publicKeyName;
    private final IOFormat keyFormat;
    private final LicenseKeyPairService service;

    @Override
    protected String doInBackground() {
        if(licenseKeyPairEntity.get().licenseKeyPair()==null)
            return "No keys are loaded in memory";

        licenseKeyPairEntity.set(service.saveKeys(licenseKeyPairEntity.get(), keyFormat, privateKeyName, publicKeyName, keyFolder));
        return "Keys have been saved.";
    }

    @Override
    protected void done(){
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("Key save interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Key save failure", e);
        }
    }
}
