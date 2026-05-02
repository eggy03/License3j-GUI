package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseSignService;
import javax0.license3j.io.IOFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingWorker;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
public class LicenseSaveWorker extends SwingWorker<String, Void> {

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final File licenseFolder;
    private final String licenseName;
    private final IOFormat licenseFormat;
    private final LicenseSignService service;

    @Override
    protected String doInBackground() {

        LicenseEntity licenseEntity = licenseEntityAtomicReference.get();

        if(!licenseEntity.hasLicense())
            return "No license is loaded in memory";

        licenseEntityAtomicReference.set(service.saveLicense(licenseEntity, licenseFolder, licenseName, licenseFormat));
        return "License has saved. Don't forget to save your keys before exiting.";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("License save interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("License save failure", e);
        }
    }
}
