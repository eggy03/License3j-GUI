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
public class KeyPairLoaderWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntityAtomicReference;
    private final File privateKeyFile;
    private final File publicKeyFile;
    private final IOFormat keyFormat;
    private final LicenseKeyPairService service;

    @Override
    protected String doInBackground() {
        licenseKeyPairEntityAtomicReference.set(service.loadKeyPair(privateKeyFile, publicKeyFile, keyFormat));
        return "Keys have been loaded in memory.";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("Key load interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Key load failure", e);
        }
    }
}
