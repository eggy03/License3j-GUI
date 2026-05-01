package io.github.eggy03.application.workers;

import io.github.eggy03.application.entity.LicenseKeyPairEntity;
import io.github.eggy03.application.services.LicenseKeyPairService;
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
public class KeyPairSaveWorker extends SwingWorker<Void, Void> {

    private final AtomicReference<LicenseKeyPairEntity> licenseKeyPairEntity;
    private final File keyFolder;
    private final String privateKeyName;
    private final String publicKeyName;
    private final IOFormat keyFormat;
    private final LicenseKeyPairService service;
    private final JTextArea textArea;

    @Override
    protected Void doInBackground() {
        service.saveKeys(licenseKeyPairEntity.get(), keyFormat, privateKeyName, publicKeyName, keyFolder);
        return null;
    }

    @Override
    protected void done(){
        try {
            get();
            textArea.setText("INFO: Keys have been saved.");
        } catch (InterruptedException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("Key save interrupted", e.getCause());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            textArea.append("ERROR: " + e.getCause().getMessage());
            log.error("Key save failure", e.getCause());
        }
    }
}
