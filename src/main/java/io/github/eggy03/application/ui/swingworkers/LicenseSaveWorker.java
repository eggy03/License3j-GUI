package io.github.eggy03.application.ui.swingworkers;

import io.github.eggy03.application.entity.LicenseEntity;
import io.github.eggy03.application.services.LicenseEntityService;
import javax0.license3j.io.IOFormat;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class LicenseSaveWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(LicenseSaveWorker.class);

    private final AtomicReference<LicenseEntity> licenseEntityAtomicReference;
    private final File licenseFolder;
    private final String licenseName;
    private final IOFormat licenseFormat;
    private final LicenseEntityService service;

    public LicenseSaveWorker(
            @NonNull AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull File licenseFolder,
            @NonNull String licenseName,
            @NonNull IOFormat licenseFormat,
            @NonNull LicenseEntityService service
    ) {
        this.licenseEntityAtomicReference = Objects.requireNonNull(licenseEntityAtomicReference, "licenseEntityAtomicReference cannot be null");
        this.licenseFolder = Objects.requireNonNull(licenseFolder, "licenseFolder cannot be null");
        this.licenseName = Objects.requireNonNull(licenseName, "licenseName cannot be null");
        this.licenseFormat = Objects.requireNonNull(licenseFormat, "licenseFormat cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
    }

    @Override
    protected String doInBackground() {

        LicenseEntity unsavedLicenseEntity = licenseEntityAtomicReference.get();

        if (unsavedLicenseEntity == null || !unsavedLicenseEntity.hasLicense())
            return "No license is loaded in memory";

        LicenseEntity savedLicenseEntity = service.saveLicense(unsavedLicenseEntity, licenseFolder, licenseName, licenseFormat);
        licenseEntityAtomicReference.set(savedLicenseEntity);
        return "License has been saved. Don't forget to save your keys before exiting.";
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.error("License save interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("License save failure", e.getCause());
        }
    }
}
