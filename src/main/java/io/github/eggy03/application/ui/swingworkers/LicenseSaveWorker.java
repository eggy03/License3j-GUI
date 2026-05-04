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
    private final LicenseEntityService service;
    private final String licenseName;
    private final IOFormat licenseFormat;
    private final File licenseFolder;

    public LicenseSaveWorker(
            @NonNull AtomicReference<LicenseEntity> licenseEntityAtomicReference,
            @NonNull LicenseEntityService service,
            @NonNull String licenseName,
            @NonNull IOFormat licenseFormat,
            @NonNull File licenseFolder
    ) {
        this.licenseEntityAtomicReference = Objects.requireNonNull(licenseEntityAtomicReference, "licenseEntityAtomicReference cannot be null");
        this.service = Objects.requireNonNull(service, "service cannot be null");
        this.licenseName = Objects.requireNonNull(licenseName, "licenseName cannot be null");
        this.licenseFormat = Objects.requireNonNull(licenseFormat, "licenseFormat cannot be null");
        this.licenseFolder = Objects.requireNonNull(licenseFolder, "licenseFolder cannot be null");
    }

    @Override
    protected String doInBackground() {

        LicenseEntity unsavedLicenseEntity = licenseEntityAtomicReference.get();

        if (unsavedLicenseEntity == null || !unsavedLicenseEntity.hasLicense())
            return "No license loaded in memory";

        LicenseEntity savedLicenseEntity = service.saveLicense(unsavedLicenseEntity, licenseFolder, licenseName, licenseFormat);
        licenseEntityAtomicReference.set(savedLicenseEntity);

        return String.format("License [name=%s, format=%s] saved to [folder=%s]", licenseName, licenseFormat, licenseFolder);
    }

    @Override
    protected void done() {
        try {
            log.info(get());
        } catch (InterruptedException e) {
            log.warn("Interrupted while saving license [name={}, format={}] to [folder={}]", licenseName, licenseFormat, licenseFolder);
            log.debug("Stack trace for interruption", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Failed to save license [name={}, format={}] to [folder={}]", licenseName, licenseFormat, licenseFolder);
            log.debug("Stack trace for failure", e.getCause());
        }
    }
}
